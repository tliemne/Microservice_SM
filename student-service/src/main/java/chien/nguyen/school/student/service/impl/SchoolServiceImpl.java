package chien.nguyen.school.student.service.impl;

import chien.nguyen.school.student.dtos.request.SchoolRequest;
import chien.nguyen.school.student.dtos.response.SchoolResponse;
import chien.nguyen.school.student.mapper.SchoolMapper;
import chien.nguyen.school.student.model.School;
import chien.nguyen.school.student.repository.SchoolRepository;
import chien.nguyen.school.student.service.SchoolService;
import com.school.common_library.exception.AppException;
import com.school.common_library.exception.ErrorCode;
import com.school.common_library.security.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;

    @Override
    public List<SchoolResponse> getSchools() {

        UserContext user = UserContext.getCurrentUser();
        requireAdmin(user);

        return schoolRepository.findAll()
                .stream()
                .map(schoolMapper::toResponse)
                .toList();
    }

    @Override
    public SchoolResponse getSchoolById(Long id) {

        UserContext user = UserContext.getCurrentUser();

        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));

        if (!canAccess(user, school)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        return schoolMapper.toResponse(school);
    }

    @Override
    public SchoolResponse createSchool(SchoolRequest request) {

        UserContext user = UserContext.getCurrentUser();
        requireAdmin(user);

        School school = schoolMapper.toEntity(request);
        return schoolMapper.toResponse(schoolRepository.save(school));
    }

    @Override
    public SchoolResponse updateSchool(Long id, SchoolRequest request) {

        UserContext user = UserContext.getCurrentUser();
        requireAdmin(user);

        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));

        schoolMapper.updateEntity(request, school);
        return schoolMapper.toResponse(schoolRepository.save(school));
    }

    @Override
    public void deleteSchool(Long id) {

        UserContext user = UserContext.getCurrentUser();
        requireAdmin(user);

        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));

        schoolRepository.delete(school);
    }



    private void requireAdmin(UserContext user) {
        if (user == null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if (!user.hasRole("ROLE_ADMIN")) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }

    private boolean canAccess(UserContext user, School school) {

        if (user == null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (user.hasRole("ROLE_ADMIN")) return true;

        if (user.hasRole("ROLE_SCHOOL_MANAGER")) {
            return Objects.equals(user.getSchoolId(), school.getId());
        }

        return false;
    }
}
