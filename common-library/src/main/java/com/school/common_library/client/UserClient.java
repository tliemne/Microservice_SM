package com.school.common_library.client;

import com.school.common_library.ApiResponse;
import com.school.common_library.configuration.FeignConfig;
import com.school.common_library.dto.UserInternalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "user-service",
        url = "localhost:8002/"
)
public interface UserClient {
    @GetMapping("/user-service/internal/users/{username}")
    ApiResponse<UserInternalResponse> getUserByUsername(@PathVariable("username") String username);
}
