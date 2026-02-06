package com.vannam.auth_service.controller;

import com.vannam.auth_service.dto.request.RoleRequest;
import com.vannam.auth_service.dto.request.SchoolManagerRequest;
import com.vannam.auth_service.dto.request.UserRequest;
import com.vannam.auth_service.dto.response.ApiResponse;
import com.vannam.auth_service.dto.response.RoleResponse;
import com.vannam.auth_service.dto.response.UserResponse;
import com.vannam.auth_service.entity.User;
import com.vannam.auth_service.exception.AppException;
import com.vannam.auth_service.exception.ErrorCode;
import com.vannam.auth_service.repository.UserRepository;
import com.vannam.auth_service.repository.httpclient.StudentClient;
import com.vannam.auth_service.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class UserController {
    UserService userService;
    UserRepository userRepository;
    StudentClient studentClient;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ApiResponse<UserResponse> createUser(@RequestBody UserRequest userRequest){
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(userRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<List<UserResponse>> getAll() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAll())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    ApiResponse<UserResponse> getById(@PathVariable("id") Long id) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getById(id))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    ApiResponse<UserResponse> update(@PathVariable("id") Long id,@RequestBody UserRequest update) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(id,update))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    ApiResponse<String> deleteById(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.<String>builder()
                .result("User has been deleted")
                .build();
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/school-manager")
    public void createSchoolManager(@RequestBody SchoolManagerRequest request){

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!user.getRole().getName().equals("SCHOOL_MANAGER")) {
            throw new AppException(ErrorCode.INVALID_ROLE);
        }

        studentClient.createSchoolManager(request);

    }

}
