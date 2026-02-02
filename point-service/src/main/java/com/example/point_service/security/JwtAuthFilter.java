//package com.example.point_service.security;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.crypto.SecretKey;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//@Component
//@Slf4j
//public class JwtAuthFilter extends OncePerRequestFilter {
//
//    @Value("${jwt.secret:MySecretKeyForAuthenticationAndAuthorizationWithMinimum256BitsLength123456789}")
//    private String jwtSecret;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain) throws ServletException, IOException {
//
//        String header = request.getHeader("Authorization");
//
//        if (header != null && header.startsWith("Bearer ")) {
//            String token = header.substring(7);
//
//            try {
//                // Validate and extract claims
//                SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
//
//                var claims = Jwts.parser()
//                        .verifyWith(key)
//                        .build()
//                        .parseSignedClaims(token)
//                        .getPayload();
//
//                Long userId = Long.parseLong(claims.getSubject());
//                String username = (String) claims.get("username");
//
//                @SuppressWarnings("unchecked")
//                List<String> roles = (List<String>) claims.get("roles");
//
//                Long schoolId = null;
//                Object schoolIdObj = claims.get("schoolId");
//                if (schoolIdObj != null) {
//                    schoolId = Long.valueOf(schoolIdObj.toString());
//                }
//
//                Long classId = null;
//                Object classIdObj = claims.get("classId");
//                if (classIdObj != null) {
//                    classId = Long.valueOf(classIdObj.toString());
//                }
//
//                Long studentId = null;
//                Object studentIdObj = claims.get("studentId");
//                if (studentIdObj != null) {
//                    studentId = Long.valueOf(studentIdObj.toString());
//                }
//
//                String dataScope = (String) claims.get("dataScope");
//
//                // Convert roles to GrantedAuthority
//                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                if (roles != null) {
//                    for (String role : roles) {
//                        String roleWithPrefix = role.startsWith("ROLE_") ? role : "ROLE_" + role;
//                        authorities.add(new SimpleGrantedAuthority(roleWithPrefix));
//                    }
//                }
//
//                // Create UserContext principal
//                UserContext userContext = new UserContext(
//                        userId, username, roles, schoolId, classId, studentId, dataScope, token
//                );
//
//                // Set Authentication in SecurityContext
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(
//                                userContext,
//                                null,
//                                authorities
//                        );
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//                log.debug("[JwtAuthFilter] User authenticated: userId={}, username={}, schoolId={}, roles={}",
//                        userId, username, schoolId, roles);
//
//            } catch (Exception e) {
//                log.warn("[JwtAuthFilter] Token validation failed: {}", e.getMessage());
//                SecurityContextHolder.clearContext();
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
