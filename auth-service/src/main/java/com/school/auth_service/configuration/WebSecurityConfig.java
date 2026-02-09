<<<<<<< HEAD:auth-service/src/main/java/com/school/auth_service/configuration/WebSecurityConfig.java
package com.school.auth_service.configuration;


import com.school.common_library.configuration.SecurityConfig;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig extends SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("---- AUTH SERVICE ĐANG ÁP DỤNG CẤU HÌNH SECURITY ----");
        return super.baseFilterChain(http);
    }
}

=======
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
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51:user-service/src/main/java/com/school/user_service/configuration/WebSecurityConfig.java
