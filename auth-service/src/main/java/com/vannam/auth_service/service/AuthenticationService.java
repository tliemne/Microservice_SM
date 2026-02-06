package com.vannam.auth_service.service;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.vannam.auth_service.dto.request.AuthenticationRequest;
import com.vannam.auth_service.dto.request.IntrospectRequest;
import com.vannam.auth_service.dto.request.RefreshTokenRequest;
import com.vannam.auth_service.dto.response.AuthenticationResponse;
import com.vannam.auth_service.dto.response.IntrospectResponse;
import com.vannam.auth_service.entity.RefreshToken;
import com.vannam.auth_service.entity.User;
import com.vannam.auth_service.exception.AppException;
import com.vannam.auth_service.exception.ErrorCode;
import com.vannam.auth_service.repository.RefreshTokenRepository;
import com.vannam.auth_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    RefreshTokenRepository refreshTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SINGNER_KEY;


    public AuthenticationResponse authenticated(AuthenticationRequest request){
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated =passwordEncoder.matches(request.getPassword(),user.getPassword());

        if(!authenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String accessToken =generateAccessToken(user);
        String refreshToken=generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .authenticated(true)
                .AccessToken(accessToken)
                .RefreshToken(refreshToken)
                .build();

    }


    public String generateAccessToken(User user){
        JWSHeader jwsHeader=new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet=new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("vannam")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(15, ChronoUnit.MINUTES).toEpochMilli()))
                .claim("userId", user.getId())
                .claim("role", user.getRole().getName())
                .claim("permissions",
                        user.getRole().getPermissions()
                        .stream()
                        .map(p -> p.getName())
                        .toList())
                .claim(
                        "dataScopes",
                        user.getDataScopes()
                                .stream()
                                .map(Enum::name)
                                .toList()
                )

                .build();
        Payload payload =new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject=new JWSObject(jwsHeader,payload);
        try {
            jwsObject.sign(new MACSigner(SINGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create Token",e);
            throw new RuntimeException(e);
        }
    }

    private String generateRefreshToken(User user) {
        String token = UUID.randomUUID().toString();


        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .user(user)
                .expiryTime(Instant.now().plus(7,ChronoUnit.DAYS))
                .build();

        refreshTokenRepository.save(refreshToken);
        return token;
    }

    public AuthenticationResponse refresh(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_REFRESH_TOKEN));

        if (token.getExpiryTime().isBefore(Instant.now())) {
            throw new AppException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        String newAccessToken = generateAccessToken(token.getUser());

        return AuthenticationResponse.builder()
                .authenticated(true)
                .AccessToken(newAccessToken)
                .RefreshToken(refreshToken)
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(request.getToken());

            boolean valid = signedJWT.verify(
                    new MACVerifier(SINGNER_KEY.getBytes())
            );

            Date exp = signedJWT.getJWTClaimsSet().getExpirationTime();

            return IntrospectResponse.builder()
                    .valid(valid && exp.after(new Date()))
                    .build();

        } catch (Exception e) {
            return IntrospectResponse.builder()
                    .valid(false)
                    .build();
        }
    }

    public void logout(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }









}
