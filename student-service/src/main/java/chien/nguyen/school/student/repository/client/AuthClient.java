package chien.nguyen.school.student.repository.client;

import chien.nguyen.school.student.dtos.response.UserInternalResponse;
import com.school.common_library.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @GetMapping("/internal/users/{username}")
    ApiResponse<UserInternalResponse> getUserByUsername(@PathVariable("username") String username);

    @GetMapping("/internal/users/{userId}/exists")
    boolean checkUserExists(@PathVariable("userId") String userId);
}