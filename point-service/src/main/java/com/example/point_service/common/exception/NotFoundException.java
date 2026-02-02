package com.example.point_service.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception khi resource không tìm thấy
 */
public class NotFoundException extends BaseApiException {
    
    public NotFoundException(String message) {
        super(message, "NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
