package com.school.common_library;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final JwtConfig jwtConfig;

    public String generateToken(String username, String role) {
        long expirationInMillis = jwtConfig.getAccessTokenValidityInSeconds() * 1000;

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationInMillis))
                .signWith(Keys.hmacShaKeyFor(jwtConfig.getSignerKey().getBytes()))
                .compact();
    }
}
