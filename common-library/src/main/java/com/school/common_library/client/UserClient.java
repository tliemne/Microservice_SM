package com.school.common_library.client;

import com.school.common_library.ApiResponse;
import com.school.common_library.dto.UserInternalResponse;
import com.school.common_library.dto.UserLoginInternalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "user-service",
        url = "localhost:8002/"
)
public interface UserClient {
    @GetMapping("/user-service/internal/users/username/{username}")
    ApiResponse<UserLoginInternalResponse> getUserByUsername(@PathVariable("username") String username);
    @GetMapping("/user-service/internal/users/id/{userId}")
    ApiResponse<UserInternalResponse> getUserById(@PathVariable("userId") String userId);
}
