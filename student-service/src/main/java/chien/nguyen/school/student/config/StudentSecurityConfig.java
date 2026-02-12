package chien.nguyen.school.student.config;

import com.school.common_library.configuration.SecurityConfig;
import com.school.common_library.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class StudentSecurityConfig extends SecurityConfig {

    public StudentSecurityConfig(JwtAuthFilter jwtAuthFilter) {
        super(jwtAuthFilter);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("---- STUDENT SERVICE SECURITY CONFIG ----");
        return super.baseFilterChain(http);
    }
}
