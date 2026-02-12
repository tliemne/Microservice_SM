package chien.nguyen.school.student.config;

import chien.nguyen.school.student.repository.StudentRepository;
import com.school.common_library.security.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("auth")
@Slf4j
@RequiredArgsConstructor
public class SecurityService {

    private final StudentRepository studentRepository;

    public boolean canAccessSchool(Long targetSchoolId) {
        UserContext user = UserContext.getCurrentUser();
        if (user == null) return false;

        if (user.isAdmin()) return true;

        if (user.isSchoolManager()) {
            return user.getSchoolId() != null && user.getSchoolId().equals(targetSchoolId);
        }

        return false;
    }

    public boolean isSelfOrAdmin(String targetUserId) {
        UserContext user = UserContext.getCurrentUser();
        if (user == null) return false;

        if (user.isAdmin()) return true;

        return user.getUserId() != null && user.getUserId().equals(targetUserId);
    }
}