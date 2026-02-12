package com.school.common_library.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserContext implements UserDetails {

    private String userId;
    private String username;
    private List<String> roles;
    private Long schoolId;
    private Long studentId;
    private String token;

    public static UserContext getCurrentUser() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof UserContext ctx) {
            return ctx;
        }

        return null;
    }


    public boolean isStudent() {
        return hasRole("ROLE_STUDENT");
    }

    public boolean isAdmin() {
        return hasRole("ROLE_ADMIN");
    }

    public boolean isSchoolManager() {
        return hasRole("ROLE_SCHOOL_MANAGER");
    }

    public boolean hasRole(String role) {
        if (roles == null) return false;

        return roles.stream()
                .anyMatch(r -> r.equalsIgnoreCase(role));
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (roles == null) return List.of();

        return roles.stream()
                .map(r -> {
                    String finalRole = r.startsWith("ROLE_") ? r : "ROLE_" + r;
                    return new SimpleGrantedAuthority(finalRole);
                })
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}