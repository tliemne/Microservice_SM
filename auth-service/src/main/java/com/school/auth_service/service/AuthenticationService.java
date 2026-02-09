package com.school.auth_service.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
<<<<<<< HEAD
import com.school.auth_service.model.User;
import com.school.auth_service.repository.UserRepository;
import com.school.common_library.JwtConfig;
import com.school.auth_service.dto.request.*;
import com.school.auth_service.dto.response.*;
import com.school.common_library.exception.AppException;
import com.school.common_library.exception.ErrorCode;
=======
import com.school.auth_service.dto.request.*;
import com.school.auth_service.dto.response.*;
import com.school.common_library.security.JwtConfig;

>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {

    UserRepository userRepository;
    JwtConfig jwtConfig;
    PasswordEncoder passwordEncoder;


    private static final Long FAKE_USER_ID = 1L;
    private static final Long FAKE_STUDENT_ID = 1001L;
    private static final Long FAKE_SCHOOL_ID = 10L;

    private static final String FAKE_USERNAME = "admin";
    private static final String FAKE_PASSWORD = "123456"; // raw password
    private static final List<String> FAKE_ROLES = List.of("ADMIN");


<<<<<<< HEAD
        String username = signedJWT.getJWTClaimsSet().getSubject();
        var user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return AuthenticationResponse.builder()
                .accessToken(generateToken(user, false))
                .refreshToken(generateToken(user, true))
                .authenticated(true)
                .build();
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(request.getToken(), false);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            log.info("Token {} has been logged out", jit);
        } catch (Exception e) {
            log.info("Token already expired or invalid");
        }
    }

    public String generateToken(User user, boolean isRefreshToken) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        long validity = isRefreshToken
                ? jwtConfig.getRefreshTokenValidityInSeconds()
                : jwtConfig.getAccessTokenValidityInSeconds();

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("school")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + validity * 1000))
                .jwtID(UUID.randomUUID().toString())
                .claim("role", isRefreshToken ? "" : buildRole(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(jwtConfig.getSignerKey().getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot sign JWT", e);
            throw new RuntimeException(e);
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(jwtConfig.getSignerKey().getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) {
            throw new RuntimeException("Unauthenticated");
        }

        return signedJWT;
    }

    private String buildRole(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            user.getRoles().forEach(role -> stringJoiner.add("ROLE_" + role));

        }
        return stringJoiner.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

=======


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51

        if (!FAKE_USERNAME.equals(request.getUsername())) {
            throw new RuntimeException("User not found");
        }

        // TEST MODE: so password trực tiếp
        if (!FAKE_PASSWORD.equals(request.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        String accessToken = generateToken(
                FAKE_USER_ID,
                FAKE_USERNAME,
                FAKE_ROLES,
                FAKE_STUDENT_ID,
                FAKE_SCHOOL_ID,
                false
        );

        String refreshToken = generateToken(
                FAKE_USER_ID,
                FAKE_USERNAME,
                FAKE_ROLES,
                FAKE_STUDENT_ID,
                FAKE_SCHOOL_ID,
                true
        );

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .authenticated(true)
                .build();
    }




    public AuthenticationResponse refreshToken(RefreshRequest request)
            throws JOSEException, ParseException {

        SignedJWT signedJWT =
                verifyToken(request.getToken(), true);

        JWTClaimsSet claims =
                signedJWT.getJWTClaimsSet();

        Long userId = claims.getLongClaim("userId");
        Long studentId = claims.getLongClaim("studentId");
        Long schoolId = claims.getLongClaim("schoolId");

        String username = claims.getSubject();

        List<String> roles =
                (List<String>) claims.getClaim("roles");

        String accessToken = generateToken(
                userId,
                username,
                roles,
                studentId,
                schoolId,
                false
        );

        String refreshToken = generateToken(
                userId,
                username,
                roles,
                studentId,
                schoolId,
                true
        );

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .authenticated(true)
                .build();
    }



    public void logout(LogoutRequest request) {

        log.info("Logout token: {}", request.getToken());
    }



    public IntrospectResponse introspect(IntrospectRequest request) {

        boolean valid = true;

        try {
            verifyToken(request.getToken(), false);
        } catch (Exception e) {
            valid = false;
        }

        return IntrospectResponse.builder()
                .valid(valid)
                .build();
    }



    public String generateToken(
            Long userId,
            String username,
            List<String> roles,
            Long studentId,
            Long schoolId,
            boolean isRefreshToken
    ) {

        JWSHeader header =
                new JWSHeader(JWSAlgorithm.HS512);

        long validity = isRefreshToken
                ? jwtConfig.getRefreshTokenValidityInSeconds()
                : jwtConfig.getAccessTokenValidityInSeconds();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()

                .subject(username)
                .issuer("school")
                .issueTime(new Date())

                .expirationTime(
                        new Date(System.currentTimeMillis() + validity * 1000)
                )

                .jwtID(UUID.randomUUID().toString())

                // ====== CUSTOM CLAIMS ======
                .claim("userId", userId)
                .claim("studentId", studentId)
                .claim("schoolId", schoolId)
                .claim("roles", isRefreshToken ? List.of() : roles)

                .build();

        Payload payload =
                new Payload(claims.toJSONObject());

        JWSObject jwsObject =
                new JWSObject(header, payload);

        try {

            jwsObject.sign(
                    new MACSigner(jwtConfig.getSignerKey().getBytes())
            );

            return jwsObject.serialize();

        } catch (JOSEException e) {

            throw new RuntimeException("Cannot sign JWT", e);
        }
    }


    private SignedJWT verifyToken(
            String token,
            boolean isRefresh
    ) throws JOSEException, ParseException {

        JWSVerifier verifier =
                new MACVerifier(jwtConfig.getSignerKey().getBytes());

        SignedJWT signedJWT =
                SignedJWT.parse(token);

        Date expiryTime =
                signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified =
                signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) {

            throw new RuntimeException("Unauthenticated");
        }

        return signedJWT;
    }

}
