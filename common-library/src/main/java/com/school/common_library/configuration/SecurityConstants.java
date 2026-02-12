package com.school.common_library.configuration;

public class SecurityConstants {
    public static final String[] PUBLIC_ENDPOINTS = {
            "/auth/login",
            "/auth/**",
            "/permissions/**",
            "/roles/**",
            "/users/**",
            "/internal/users/**",
            "/points/**",
            "/students/**",
            "/schools/**",
            "/classes/**"
    };

}
