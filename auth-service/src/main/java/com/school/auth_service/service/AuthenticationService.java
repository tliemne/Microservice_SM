package com.school.auth_service.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.school.auth_service.model.User;
import com.school.auth_service.repository.UserRepository;
import com.school.common_library.security.JwtConfig;
import com.school.auth_service.dto.request.*;
import com.school.auth_service.dto.response.*;
import com.school.common_library.exception.AppException;
import com.school.common_library.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {

    UserRepository userRepository;
    JwtConfig jwtConfig;
    PasswordEncoder passwordEncoder;
    StringRedisTemplate redisTemplate;

    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (Exception e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws JOSEException, ParseException {
        String input = request.getToken();
        String username;
        String jit;

        if (!input.contains(".")) {
            jit = input;
            username = (String) redisTemplate.opsForValue().get("rt_owner:" + jit);

            if (username == null) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }

            if (!Boolean.TRUE.equals(redisTemplate.hasKey("rt:" + username + ":" + jit))) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
        } else {
            var signedJWT = verifyToken(input, true);
            username = signedJWT.getJWTClaimsSet().getSubject();
            jit = signedJWT.getJWTClaimsSet().getJWTID();
        }

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        redisTemplate.delete("rt:" + username + ":" + jit);
        redisTemplate.delete("rt_owner:" + jit);
        redisTemplate.opsForSet().remove("user_tokens:" + username, jit);

        return AuthenticationResponse.builder()
                .accessToken(generateToken(user, false))
                .refreshToken(generateToken(user, true))
                .authenticated(true)
                .build();
    }

    public void logout(LogoutRequest request) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(request.getToken());
            String jit = signedJWT.getJWTClaimsSet().getJWTID();
            String username = signedJWT.getJWTClaimsSet().getSubject();
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            long ttl = (expiryTime.getTime() - System.currentTimeMillis()) / 1000;
            if (ttl > 0) {
                redisTemplate.opsForValue().set("bl:" + jit, "1", ttl, TimeUnit.SECONDS);
            }

            redisTemplate.delete("rt:" + username + ":" + jit);
            redisTemplate.opsForSet().remove("user_tokens:" + username, jit);

            log.info("User {} logged out successfully for session {}", username, jit);

        } catch (Exception e) {
            log.error("Logout error: {}", e.getMessage());
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }

    public String generateToken(User user, boolean isRefreshToken) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        String jit = UUID.randomUUID().toString();
        long validity = isRefreshToken
                ? jwtConfig.getRefreshTokenValidityInSeconds()
                : jwtConfig.getAccessTokenValidityInSeconds();
        List<String> scopes = isRefreshToken ? List.of() : user.getScopes().stream()
                .map(s -> {
                    if ("ALL".equalsIgnoreCase(s.getType())) {
                        return "ALL";
                    }
                    return s.getType() + ":" + s.getValue();
                })
                .toList();

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("school")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + validity * 1000))
                .jwtID(jit)
                .claim("roles", isRefreshToken ? List.of() : user.getRoles().stream()
                        .map(role -> "ROLE_" + role.getName())
                        .toList())
                .claim("scope", scopes)
                .claim("userId", user.getId())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(jwtConfig.getSignerKey().getBytes()));
            String token = jwsObject.serialize();
            if (isRefreshToken) {
                redisTemplate.opsForValue().set(
                        "rt:" + user.getUsername() + ":" + jit,
                        "active",
                        validity,
                        TimeUnit.SECONDS
                );
                redisTemplate.opsForValue().set("rt_owner:" + jit, user.getUsername(), validity, TimeUnit.SECONDS);
                String userSetKey = "user_tokens:" + user.getUsername();
                redisTemplate.opsForSet().add(userSetKey, jit);
                redisTemplate.expire(userSetKey, validity, TimeUnit.SECONDS);
            }

            return token;
        } catch (JOSEException e) {
            log.error("Cannot sign JWT", e);
            throw new RuntimeException(e);
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);

        String jit = signedJWT.getJWTClaimsSet().getJWTID();

        if (Boolean.TRUE.equals(redisTemplate.hasKey("bl:" + jit))) {
            log.warn("Token {} is blacklisted!", jit);
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        JWSVerifier verifier = new MACVerifier(jwtConfig.getSignerKey().getBytes());

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }


        if (isRefresh) {
            String username = signedJWT.getJWTClaimsSet().getSubject();
            Boolean hasKey = redisTemplate.hasKey("rt:" + username + ":" + jit);
            if (Boolean.FALSE.equals(hasKey)) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
        }
        return signedJWT;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));


        boolean authenticated = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        var accessToken = generateToken(user, false);
        var refreshToken = generateToken(user, true);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .authenticated(true)
                .build();
    }
}