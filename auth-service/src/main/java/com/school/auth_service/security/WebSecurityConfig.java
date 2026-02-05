package com.school.auth_service.security;

import com.school.common_library.configuration.SecurityConfig;
import com.school.common_library.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig extends SecurityConfig {

    // BẮT BUỘC PHẢI CÓ
    public WebSecurityConfig(JwtAuthFilter jwtAuthFilter) {
        super(jwtAuthFilter);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        System.out.println("---- AUTH SERVICE SECURITY ACTIVE ----");

        return super.baseFilterChain(http);
    }
}
