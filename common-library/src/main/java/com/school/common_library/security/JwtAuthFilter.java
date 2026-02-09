package com.school.common_library.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {

            Claims claims = jwtUtils.validateToken(token);

            String username = claims.getSubject();

            List<String> roles =
                    claims.get("roles", List.class);

            Long schoolId =
                    claims.get("schoolId", Long.class);


            Long studentId =
                    claims.get("studentId", Long.class);

            // (optional) userId nếu sau này dùng
            Long userId =
                    claims.get("userId", Long.class);

            UserContext userContext = new UserContext(
                    userId,
                    username,
                    roles,
                    studentId,
                    schoolId,
                    token
            );

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userContext,
                            null,
                            userContext.getAuthorities()
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

        } catch (Exception e) {
            log.error("JWT invalid: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}