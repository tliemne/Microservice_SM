package com.school.auth_service.controller;
import com.school.common_library.ApiResponse;
import com.school.auth_service.dto.request.*;
import com.school.auth_service.dto.response.*;
import com.school.auth_service.service.*;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> create(@RequestBody @Valid UserCreationRequest request) {
        log.info("Controller: Creating User {}", request.getUsername());
        return ApiResponse.<UserResponse>builder()
                .message("User created successfully")
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAll() {
        return ApiResponse.<List<UserResponse>>builder()
                .message("Get all User successfully")
                .result(userService.getUsers())
                .build();
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> update(
            @PathVariable String userId,
            @RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .message("User updated successfully")
                .result(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<UserResponse> delete(@PathVariable String userId) {

        return ApiResponse.<UserResponse>builder()
                .message("User deleted successfully")
                .result(userService.deleteUser(userId))
                .build();
    }

    @PutMapping("/{userId}/restore")
    ApiResponse<UserResponse> restore(@PathVariable String userId) {

        return ApiResponse.<UserResponse>builder()
                .message("User " + userId + " has been restored successfully")
                .result(userService.restore(userId))
                .build();
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        return  ApiResponse.<UserResponse>builder()
                .message("Get my user")
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{userId}/add-role")
    public ApiResponse<UserResponse> addRole(@PathVariable String userId ,@RequestBody String roleName) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.addRoleForUser(userId, roleName))
                .build();
    }
}
