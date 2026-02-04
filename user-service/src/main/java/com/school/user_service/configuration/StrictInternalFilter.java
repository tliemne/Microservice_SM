package com.school.user_service.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StrictInternalFilter extends OncePerRequestFilter {


    private static final String INTERNAL_SECRET = "yDwBpFAkbivoiEaENcs0Z71NSkz5xbXx+3qGa5X/GCc=";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Chỉ áp dụng cho các đường dẫn nội bộ
        if (path.contains("/internal/")) {
            String secretHeader = request.getHeader("X-Internal-Secret");

            if (!INTERNAL_SECRET.equals(secretHeader)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Postman khong co cua vao day!\"}");
            }

            UsernamePasswordAuthenticationToken internalAuth = new UsernamePasswordAuthenticationToken(
                    "SYSTEM", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_INTERNAL")));
            SecurityContextHolder.getContext().setAuthentication(internalAuth);
        }

        filterChain.doFilter(request, response);
    }
}