package com.vannam.auth_service.controller;


import com.vannam.auth_service.dto.response.ApiResponse;
import com.vannam.auth_service.dto.response.UserDetailResponse;
import com.vannam.auth_service.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/user")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class InternallUserController {

    UserService userService;

    @GetMapping("/{userId}")
    public UserDetailResponse getUserDetail(@PathVariable Long userId) {
        return userService.getUserDetail(userId);
    }
}
