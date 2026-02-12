package com.school.auth_service.controller;

import com.school.auth_service.service.UserInternalService;
import com.school.common_library.ApiResponse;
import com.school.auth_service.dto.response.UserInternalResponse;
import com.school.auth_service.service.UserService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserInternalController {
    UserInternalService userInternalService;
    @GetMapping("/{username}")
    public ApiResponse<UserInternalResponse> getUserByUsername(@PathVariable String username) {
        return ApiResponse.<UserInternalResponse>builder()
                .result(userInternalService.getInternalUser(username))
                .build();
    }

    @GetMapping("/{userId}/exists")
    public boolean existsById(@PathVariable String userId) {
        return userInternalService.existsById(userId);
    }

}
