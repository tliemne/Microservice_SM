package com.example.point_service.security;

import com.school.common_library.security.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("auth")
@Slf4j
@RequiredArgsConstructor
public class SecurityService {

    public boolean isSchoolManager(String targetSchoolId) {

        UserContext user = UserContext.getCurrentUser();

        if (user == null) {
            return false;
        }

        log.info("DEBUG: User hiện tại: [{}], Roles: {}", user.getUsername(), user.getRoles());

        if (user.getSchoolId() == null) {

            return false;
        }

        boolean hasRole = user.isSchoolManager();

        if (!hasRole) {
            return false;
        }

        String userSchoolIdStr = String.valueOf(user.getSchoolId());
        boolean isMatch = userSchoolIdStr.equals(targetSchoolId);


        if (isMatch) {
            log.info("SUCCESS: Quyền hợp lệ!");
        } else {
            log.warn("FAILED: SchoolId không khớp!");
        }

        return isMatch;
    }

}