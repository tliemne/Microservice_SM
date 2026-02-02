package com.example.point_service.common.exception;

import com.example.point_service.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Global exception handler cho tất cả API endpoints
 * Xử lý tất cả exceptions và return consistent error response
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    /**
     * Handle AuthorizationDeniedException (403 Forbidden) from Spring Security
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthorizationDeniedException(
            AuthorizationDeniedException e, WebRequest request) {
        log.warn("Authorization denied: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error("Permission denied", "PERMISSION_DENIED"));
    }
    
    /**
     * Handle AuthorizationException (403 Forbidden)
     */
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthorizationException(
            AuthorizationException e, WebRequest request) {
        log.warn("Authorization denied: {}", e.getMessage());
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ApiResponse.error(e.getMessage(), e.getErrorCode()));
    }
    
    /**
     * Handle NotFoundException (404 Not Found)
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFoundException(
            NotFoundException e, WebRequest request) {
        log.warn("Resource not found: {}", e.getMessage());
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ApiResponse.error(e.getMessage(), e.getErrorCode()));
    }
    
    /**
     * Handle ValidationException (400 Bad Request)
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(
            ValidationException e, WebRequest request) {
        log.warn("Validation error: {}", e.getMessage());
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ApiResponse.error(e.getMessage(), e.getErrorCode()));
    }
    
    /**
     * Handle all other exceptions (500 Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericException(
            Exception e, WebRequest request) {
        log.error("Internal server error: ", e);
        return ResponseEntity
                .status(500)
                .body(ApiResponse.error("Internal server error", "INTERNAL_ERROR"));
    }
}
