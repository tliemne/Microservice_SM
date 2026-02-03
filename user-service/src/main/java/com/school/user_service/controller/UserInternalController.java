package com.school.user_service.controller;

import com.school.common_library.ApiResponse;
import com.school.common_library.dto.UserInternalResponse;
import com.school.common_library.dto.UserLoginInternalResponse;
import com.school.user_service.service.UserService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserInternalController {
    UserService userService;
    @GetMapping("/username/{username}")
    public ApiResponse<UserLoginInternalResponse> getUserByUsername(@PathVariable String username) {
        return ApiResponse.<UserLoginInternalResponse>builder()
                .result(userService.getInternalLoginUser(username))
                .build();
    }

    @GetMapping("/id/{userId}")
    public ApiResponse<UserInternalResponse> getUserById(@PathVariable String userId) {
        return ApiResponse.<UserInternalResponse>builder()
                .result(userService.getInternalUser(userId))
                .build();
    }
}
