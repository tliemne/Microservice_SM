package com.school.common_library.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtConfig jwtConfig;

    // Verify + decode token
    public Claims validateToken(String token) {

        return Jwts.parser()
                .verifyWith(
                        Keys.hmacShaKeyFor(
                                jwtConfig.getSignerKey().getBytes()
                        )
                )
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
