package com.school.user_service.controller;

import com.school.common_library.ApiResponse;
import com.school.common_library.dto.UserInternalResponse;
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
    @GetMapping("/{username}")
    public ApiResponse<UserInternalResponse> getUserByUsername(@PathVariable String username) {
        return ApiResponse.<UserInternalResponse>builder()
                .result(userService.getInternalUser(username))
                .build();
    }

}
