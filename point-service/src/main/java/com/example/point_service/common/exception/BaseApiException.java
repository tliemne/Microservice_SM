package com.example.point_service.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Base exception class cho tất cả API exceptions
 * Mỗi exception con sẽ có errorCode và httpStatus riêng
 */
public class BaseApiException extends RuntimeException {
    
    private final String errorCode;
    private final HttpStatus httpStatus;
    
    public BaseApiException(String message, String errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
