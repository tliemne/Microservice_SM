package com.example.point_service.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserContext {
    private Long userId;
    private String username;
    private List<String> roles;
    private Long schoolId;
    private Long classId;
    private Long studentId;
    private String dataScope;
    private String authToken;


//    public static UserContext getCurrentUser() {
//        var authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            log.warn("[UserContext.getCurrentUser] No authentication in SecurityContext");
//            throw new IllegalStateException("User not authenticated");
//        }
//
//        Object principal = authentication.getPrincipal();
//        if (!(principal instanceof UserContext)) {
//            log.warn("[UserContext.getCurrentUser] Principal is not UserContext: {}", principal.getClass().getName());
//            throw new IllegalStateException("Principal is not UserContext");
//        }
//
//        return (UserContext) principal;
//    }
    public static UserContext getCurrentUser() {

        var authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();


        if (authentication == null || !authentication.isAuthenticated()) {

            log.warn("[TEST MODE] No authentication -> using FAKE ADMIN");

            return new UserContext(
                    1L,                     // userId
                    "test-admin",           // username
                    List.of("ADMIN"),       // roles
                    1L,                     // schoolId
                    null,                   // classId
                    null,                   // studentId
                    "ALL",                  // dataScope
                    null                    // token
            );
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserContext)) {
            log.warn("[UserContext] Principal is not UserContext: {}",
                    principal.getClass().getName());

            return new UserContext(
                    1L,
                    "test-admin",
                    List.of("ADMIN"),
                    1L,
                    null,
                    null,
                    "ALL",
                    null
            );
        }

        return (UserContext) principal;
    }


    public boolean isAdmin() {
        return hasRole("ADMIN");
    }

    public boolean isSchoolManager() {
        return hasRole("SCHOOL_MANAGER") || hasRole("SM");
    }

    public boolean isStudent() {
        return hasRole("STUDENT");
    }

    public boolean hasRole(String role) {
        if (roles == null || roles.isEmpty()) {
            return false;
        }
        String roleToCheck = role.startsWith("ROLE_") ? role.substring(5) : role;
        return roles.stream()
                .anyMatch(r -> r.equalsIgnoreCase(roleToCheck));
    }

    public Collection<String> getAuthorities() {
        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }
        return roles.stream()
                .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                .collect(Collectors.toList());
    }

    public boolean canAccessSchool(Long schoolId) {
        if (isAdmin()) {
            return true;
        }
        if (isSchoolManager()) {
            return this.schoolId != null && this.schoolId.equals(schoolId);
        }
        return false;
    }

    public boolean canAccessStudent(Long studentId) {
        if (isAdmin()) {
            return true;
        }
        if (isSchoolManager()) {
            return true; // SM can access any student in their school
        }
        if (isStudent()) {
            return this.studentId != null && this.studentId.equals(studentId);
        }
        return false;
    }

    @Override
    public String toString() {
        return "UserContext{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                ", schoolId=" + schoolId +
                ", classId=" + classId +
                ", studentId=" + studentId +
                ", dataScope='" + dataScope + '\'' +
                '}';
    }
}
