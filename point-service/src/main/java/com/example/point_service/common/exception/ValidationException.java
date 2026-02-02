package com.example.point_service.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception khi validation input fails
 */
public class ValidationException extends BaseApiException {
    
    public ValidationException(String message) {
        super(message, "VALIDATION_ERROR", HttpStatus.BAD_REQUEST);
    }
}
