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

            List<String> scopes = claims.get("scope", List.class);
            Long schoolId = null;
            Long studentId = null;
            if (scopes != null) {
                for (String s : scopes) {
                    if (s.equals("ALL")) {
                        log.debug("User {} has ALL access scope", username);
                        continue;
                    }

                    try {
                        if (s.contains(":")) {
                            String[] parts = s.split(":");
                            String type = parts[0];
                            String value = parts[1];

                            if ("SM_SCOPE".equals(type)) {
                                schoolId = Long.parseLong(value);
                            } else if ("STU_SCOPE".equals(type) || "STUDENT_SCOPE".equals(type)) {
                                studentId = Long.parseLong(value);
                            }
                        }
                    } catch (Exception e) {
                        log.warn("Bỏ qua scope định dạng sai: {}", s);
                    }
                }
            }

            Object userIdObj = claims.get("userId");
            String userId = userIdObj != null ? userIdObj.toString() : null;

            UserContext userContext = new UserContext(
                    userId,
                    username,
                    roles,
                    schoolId,
                    studentId,
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
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}