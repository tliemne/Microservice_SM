//package com.school.user_service.configuration;
//
//import com.school.common_library.configuration.SecurityConfig;
//import org.springframework.context.annotation.*;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class WebSecurityConfig extends SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        System.out.println("---- ĐANG ÁP DỤNG CẤU HÌNH SECURITY ----");
//        return super.baseFilterChain(http);
//    }
//}
//
