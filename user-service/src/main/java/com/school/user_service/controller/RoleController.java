package com.school.user_service.controller;

import com.school.common_library.*;
import com.school.user_service.dto.request.RoleRequest;
import com.school.user_service.dto.response.RoleResponse;
import com.school.user_service.service.RoleService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> delete(@PathVariable String role) {
        roleService.delete(role);
        return ApiResponse.<Void>builder()
                .message("Role '" + role + "' has been soft deleted")
                .build();
    }

    @PutMapping("/{role}/restore")
    ApiResponse<RoleResponse> restore(@PathVariable String role) {
        return ApiResponse.<RoleResponse>builder()
                .message("Role restored successfully")
                .result(roleService.restore(role))
                .build();
    }
}
