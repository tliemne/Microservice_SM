package chien.nguyen.school.student.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import chien.nguyen.school.student.dtos.request.SchoolRequest;
import chien.nguyen.school.student.dtos.response.SchoolResponse;
import chien.nguyen.school.student.mapper.SchoolMapper;
import chien.nguyen.school.student.model.School;
import chien.nguyen.school.student.repository.SchoolRepository;
import chien.nguyen.school.student.service.SchoolService;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;

    @Override
    public List<SchoolResponse> getSchools(Authentication authentication) {

        if (hasRole(authentication, "ADMIN")) {
            return schoolRepository.findAll()
                    .stream()
                    .map(schoolMapper::toResponse)
                    .toList();
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @Override
    public SchoolResponse getSchoolById(Authentication authentication, Long id) {

        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!canAccess(authentication, school))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        return schoolMapper.toResponse(school);
    }

    @Override
    public SchoolResponse createSchool(Authentication authentication, SchoolRequest request) {

        if (!hasRole(authentication, "ADMIN"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        School school = schoolMapper.toEntity(request);
        return schoolMapper.toResponse(schoolRepository.save(school));
    }

    @Override
    public SchoolResponse updateSchool(Authentication authentication, Long id, SchoolRequest request) {

        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!hasRole(authentication, "ADMIN"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        schoolMapper.updateEntity(request, school);
        return schoolMapper.toResponse(schoolRepository.save(school));
    }

    @Override
    public void deleteSchool(Authentication authentication, Long id) {

        if (!hasRole(authentication, "ADMIN"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        schoolRepository.deleteById(id);
    }

    private boolean canAccess(Authentication authentication, School school) {

        if (hasRole(authentication, "ADMIN")) return true;

        Map<?, ?> details = (Map<?, ?>) authentication.getDetails();
        String scope = String.valueOf(details.get("dataScope"));

        if ("SCHOOL".equals(scope)) {
            Long schoolId = Long.valueOf(String.valueOf(details.get("schoolId")));
            return school.getId().equals(schoolId);
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
