package com.school.common_library.configuration;

public class SecurityConstants {
    public static final String[] PUBLIC_ENDPOINTS = {
            "/auth/login",
            "/auth/**",
            "/permissions/**",
            "/roles/**",
            "/schools/**",
            "/classes/**",
            "/students/**",
            "/users/**",
            "/internal/users/username/**",
            "/internal/users/id/**",
    };

}
