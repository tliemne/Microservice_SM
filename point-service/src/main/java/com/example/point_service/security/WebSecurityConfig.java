package com.example.point_service.security;

import com.school.common_library.configuration.SecurityConfig;
import com.school.common_library.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
//@RequiredArgsConstructor
public class WebSecurityConfig extends SecurityConfig {

    public WebSecurityConfig(JwtAuthFilter jwtAuthFilter) {
        super(jwtAuthFilter);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        System.out.println("---- POINT SERVICE SECURITY ACTIVE ----");

        return super.baseFilterChain(http);
    }
}

