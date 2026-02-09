package com.school.common_library;

import com.school.common_library.exception.ErrorCode;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    @Builder.Default
    int code = 1000;

    @Builder.Default
    String message = "Success";
    T result;

    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder()
                .code(1000)
                .message("Success")
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(1000)
                .message("Success")
                .result(data)
                .build();
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .code(1000)
                .message(message)
                .result(data)
                .build();
    }


    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return ApiResponse.<T>builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .build();
    }
}
