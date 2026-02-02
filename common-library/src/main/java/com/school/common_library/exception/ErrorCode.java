package com.school.common_library.exception;

import lombok.*;
import org.springframework.http.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid Key", HttpStatus.BAD_REQUEST),
    USERNAME_EXISTED(1002, "Username is existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User is not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You don't have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    FIELD_REQUIRED(1009, "This field is mandatory", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(1010, "Permission is existed", HttpStatus.BAD_REQUEST ),
    PERMISSION_NAME_REQUIRED(1011, "Permission name is mandatory", HttpStatus.BAD_REQUEST ),
    PERMISSION_NOT_EXISTED(1012, "Permission is not existed" , HttpStatus.NOT_FOUND ),
    ROLE_NOT_EXISTED(1013, "Role is not existed" , HttpStatus.NOT_FOUND );



    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;
}
