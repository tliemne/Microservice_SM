package com.vannam.auth_service.controller;


import com.nimbusds.jose.JOSEException;
import com.vannam.auth_service.dto.request.AuthenticationRequest;
import com.vannam.auth_service.dto.request.IntrospectRequest;
import com.vannam.auth_service.dto.request.LogoutRequest;
import com.vannam.auth_service.dto.request.RefreshTokenRequest;
import com.vannam.auth_service.dto.response.ApiResponse;
import com.vannam.auth_service.dto.response.AuthenticationResponse;
import com.vannam.auth_service.dto.response.IntrospectResponse;
import com.vannam.auth_service.service.AuthenticationService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RequiredArgsConstructor
@Builder
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticated(@RequestBody AuthenticationRequest request){
        AuthenticationResponse result=authenticationService.authenticated(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();



    }
    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request){
        AuthenticationResponse result=authenticationService.refresh(request.getReFreshToken());
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();



    }
    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)throws JOSEException, ParseException {
        IntrospectResponse result= authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();

    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(@RequestBody LogoutRequest request){
        authenticationService.logout(request.getRefreshToken());
        return ApiResponse.<String>builder()
                .result("Logout")
                .build();

    }
}
