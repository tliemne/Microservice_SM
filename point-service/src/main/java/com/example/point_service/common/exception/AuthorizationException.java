package com.example.point_service.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception khi user không có quyền truy cập
 */
public class AuthorizationException extends BaseApiException {
    
    public AuthorizationException(String message) {
        super(message, "AUTHORIZATION_ERROR", HttpStatus.FORBIDDEN);
    }
}
