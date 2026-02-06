package com.vannam.auth_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),

    INVALID_KEY(1001, "Invalid request", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1003, "User not found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1004, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1005, "Access denied", HttpStatus.FORBIDDEN),
    INVALID_ROLE(1006, "Invalid role", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTED(1007, "Permission not found", HttpStatus.NOT_FOUND),
    PERMISSION_EXISTED(1012, "Permission already exists", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(1008, "Role not found", HttpStatus.NOT_FOUND),
    ROLE_EXISTED(1009, "Role already exists", HttpStatus.BAD_REQUEST),
    INVALID_REFRESH_TOKEN(1010, "Invalid refresh token", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_EXPIRED(1011, "Refresh token expired", HttpStatus.BAD_REQUEST);





    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
