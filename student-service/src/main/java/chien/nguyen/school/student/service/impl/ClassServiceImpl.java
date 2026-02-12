package chien.nguyen.school.student.service.impl;

import chien.nguyen.school.student.dtos.request.ClassRequest;
import chien.nguyen.school.student.dtos.response.ClassResponse;
import chien.nguyen.school.student.mapper.ClassMapper;
import chien.nguyen.school.student.model.ClassRoom;
import chien.nguyen.school.student.model.School;
import chien.nguyen.school.student.repository.ClassRepository;
import chien.nguyen.school.student.repository.SchoolRepository;
import chien.nguyen.school.student.service.ClassService;
import com.school.common_library.exception.AppException;
import com.school.common_library.exception.ErrorCode;
import com.school.common_library.security.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final SchoolRepository schoolRepository;
    private final ClassMapper classMapper;

    @Override
    public List<ClassResponse> getClasses() {

        UserContext user = UserContext.getCurrentUser();
        if (user == null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        List<ClassRoom> classes;

        if (user.hasRole("ROLE_ADMIN")) {
            classes = classRepository.findAll();
        } else if (user.hasRole("ROLE_SCHOOL")) {
            classes = classRepository.findBySchool_Id(user.getSchoolId());
        } else {
            // STUDENT chỉ xem class của chính mình
            classes = classRepository.findByStudents_Id(user.getStudentId());
        }

        return classes.stream()
                .map(classMapper::toResponse)
                .toList();
    }

    @Override
    public ClassResponse getClassById(Long id) {

        ClassRoom classRoom = classRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CLASSES_NOT_EXISTED));

        if (!canAccess(classRoom)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        return classMapper.toResponse(classRoom);
    }

    @Override
    public ClassResponse createClass(ClassRequest request) {

        UserContext user = UserContext.getCurrentUser();
        if (user == null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        School school;

        if (user.hasRole("ROLE_ADMIN")) {

            if (request.getSchoolId() == null) {
                throw new AppException(ErrorCode.FIELD_REQUIRED);
            }

            school = schoolRepository.findById(request.getSchoolId())
                    .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));

        } else {

            if (!user.hasRole("ROLE_SCHOOL")) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }

            school = schoolRepository.findById(user.getSchoolId())
                    .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));
        }

        ClassRoom classRoom = classMapper.toEntity(request);
        classRoom.setSchool(school);

        return classMapper.toResponse(classRepository.save(classRoom));
    }

    @Override
    public ClassResponse updateClass(Long id, ClassRequest request) {

        ClassRoom classRoom = classRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CLASSES_NOT_EXISTED));

        if (!canAccess(classRoom)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        classMapper.updateEntity(request, classRoom);

        if (UserContext.getCurrentUser().hasRole("ROLE_ADMIN")
                && request.getSchoolId() != null) {

            School school = schoolRepository.findById(request.getSchoolId())
                    .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));

            classRoom.setSchool(school);
        }

        return classMapper.toResponse(classRepository.save(classRoom));
    }

    @Override
    public void deleteClass(Long id) {

        ClassRoom classRoom = classRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CLASSES_NOT_EXISTED));

        if (!canAccess(classRoom)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        classRepository.delete(classRoom);
    }

    private boolean canAccess(ClassRoom classRoom) {

        UserContext user = UserContext.getCurrentUser();

        if (user.hasRole("ROLE_ADMIN")) return true;

        if (user.hasRole("ROLE_SCHOOL")) {
            return Objects.equals(
                    classRoom.getSchool().getId(),
                    user.getSchoolId()
            );
        }

        // student: chỉ xem class của mình
        return classRoom.getStudents().stream()
                .anyMatch(s ->
                        Objects.equals(s.getId(), user.getStudentId())
                );
    }
}
