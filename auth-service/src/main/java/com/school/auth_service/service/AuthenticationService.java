    package com.school.auth_service.service;

    import com.nimbusds.jose.*;
    import com.nimbusds.jose.crypto.MACSigner;
    import com.nimbusds.jose.crypto.MACVerifier;
    import com.nimbusds.jwt.JWTClaimsSet;
    import com.nimbusds.jwt.SignedJWT;
    import com.school.common_library.JwtConfig;
    import com.school.common_library.client.UserClient;
    import com.school.auth_service.dto.request.*;
    import com.school.auth_service.dto.response.*;
    import com.school.common_library.configuration.SecurityConfig;
    import com.school.common_library.dto.UserInternalResponse;
    import com.school.common_library.exception.AppException;
    import com.school.common_library.exception.ErrorCode;
    import lombok.AccessLevel;
    import lombok.RequiredArgsConstructor;
    import lombok.experimental.FieldDefaults;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.context.annotation.Bean;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.text.ParseException;
    import java.util.Date;
    import java.util.StringJoiner;
    import java.util.UUID;

    @Service
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    @Slf4j
    public class AuthenticationService {

        JwtConfig jwtConfig;
        UserClient userClient;
        PasswordEncoder passwordEncoder;

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
            var signedJWT = verifyToken(request.getToken(), true);

            String username = signedJWT.getJWTClaimsSet().getSubject();
            var userResponse = userClient.getUserByUsername(username);
            var user = userResponse.getResult();

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

        public String generateToken(UserInternalResponse user, boolean isRefreshToken) {
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

        private String buildRole(UserInternalResponse user) {
            StringJoiner stringJoiner = new StringJoiner(" ");
            if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                user.getRoles().forEach(role -> stringJoiner.add("ROLE_" + role));

            }
            return stringJoiner.toString();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
            var userResponse = userClient.getUserByUsername(request.getUsername());
            if (userResponse == null || userResponse.getResult() == null) {
                throw new AppException(ErrorCode.USER_NOT_EXISTED);
            }
            var user = userResponse.getResult();

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