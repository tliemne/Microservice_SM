package com.school.common_library.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final JwtConfig jwtConfig;


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
