package chien.nguyen.school.student.service.impl;

import chien.nguyen.school.student.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import chien.nguyen.school.student.dtos.request.ClassRequest;
import chien.nguyen.school.student.dtos.response.ClassResponse;
import chien.nguyen.school.student.mapper.ClassMapper;
import chien.nguyen.school.student.model.ClassRoom;
import chien.nguyen.school.student.model.School;
import chien.nguyen.school.student.repository.ClassRepository;
import chien.nguyen.school.student.repository.SchoolRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final SchoolRepository schoolRepository;
    private final ClassMapper classMapper;

    @Override
    public List<ClassResponse> getClasses(Authentication authentication) {

        Map<?, ?> details = (Map<?, ?>) authentication.getDetails();
        String scope = String.valueOf(details.get("dataScope"));

        List<ClassRoom> classes;

        switch (scope) {
            case "ALL" -> classes = classRepository.findAll();

            case "SCHOOL" -> {
                Long schoolId = Long.valueOf(details.get("schoolId").toString());
                classes = classRepository.findBySchool_Id(schoolId);
            }

            default -> throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return classes.stream()
                .map(classMapper::toResponse)
                .toList();
    }

    @Override
    public ClassResponse getClassById(Authentication authentication, Long id) {

        ClassRoom classRoom = classRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!canAccess(authentication, classRoom)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return classMapper.toResponse(classRoom);
    }

    @Override
    public ClassResponse createClass(Authentication authentication, ClassRequest request) {

        ClassRoom classRoom = classMapper.toEntity(request);

        if (hasRole(authentication, "ADMIN")) {

            if (request.getSchoolId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "schoolId is required");
            }

            School school = schoolRepository.findById(request.getSchoolId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "School not found"));

            classRoom.setSchool(school);
            return classMapper.toResponse(classRepository.save(classRoom));
        }

        Map<?, ?> details = (Map<?, ?>) authentication.getDetails();
        String scope = String.valueOf(details.get("dataScope"));

        if ("SCHOOL".equals(scope)) {

            Long schoolId = Long.valueOf(details.get("schoolId").toString());

            School school = schoolRepository.findById(schoolId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "School not found"));

            classRoom.setSchool(school);
            return classMapper.toResponse(classRepository.save(classRoom));
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @Override
    public ClassResponse updateClass(Authentication authentication, Long id, ClassRequest request) {

        ClassRoom classRoom = classRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!canAccess(authentication, classRoom)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        classMapper.updateEntity(request, classRoom);

        if (hasRole(authentication, "ADMIN") && request.getSchoolId() != null) {

            School school = schoolRepository.findById(request.getSchoolId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "School not found"));

            classRoom.setSchool(school);
        }

        return classMapper.toResponse(classRepository.save(classRoom));
    }

    @Override
    public void deleteClass(Authentication authentication, Long id) {

        ClassRoom classRoom = classRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!canAccess(authentication, classRoom)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        classRepository.delete(classRoom);
    }

    private boolean canAccess(Authentication authentication, ClassRoom classRoom) {

        if (hasRole(authentication, "ADMIN")) return true;

        Map<?, ?> details = (Map<?, ?>) authentication.getDetails();
        String scope = String.valueOf(details.get("dataScope"));

        if ("SCHOOL".equals(scope)) {
            Long schoolId = Long.valueOf(details.get("schoolId").toString());
            return Objects.equals(classRoom.getSchool().getId(), schoolId);
        }

        return false;
    }

    private boolean hasRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .anyMatch(r ->
                        r.getAuthority().equals(role) ||
                                r.getAuthority().equals("ROLE_" + role)
                );
    }
}
