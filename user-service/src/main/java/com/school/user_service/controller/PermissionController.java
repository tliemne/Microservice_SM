package com.school.user_service.controller;

import com.school.common_library.ApiResponse;
import com.school.user_service.dto.request.PermissionRequest;
import com.school.user_service.dto.response.PermissionResponse;
import com.school.user_service.service.PermissionService;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    public ApiResponse<PermissionResponse> create(@RequestBody @Valid PermissionRequest request) {
        log.info("Controller: Creating permission {}", request.getName());
        return ApiResponse.<PermissionResponse>builder()
                .message("Permission created successfully")
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .message("Get all permission successfully")
                .result(permissionService.getAll())
                .build();
    }

    @PutMapping("/{permissionName}")
    public ApiResponse<PermissionResponse> update(
            @PathVariable String permissionName,
            @RequestBody @Valid PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .message("Permission updated successfully")
                .result(permissionService.update(permissionName, request))
                .build();
    }

    @DeleteMapping("/{permissionName}")
    public ApiResponse<String> delete(@PathVariable String permissionName) {
        permissionService.delete(permissionName);
        return ApiResponse.<String>builder()
                .result("Permission has been deleted (soft delete)")
                .build();
    }

    @PutMapping("/{permission}/restore")
    ApiResponse<PermissionResponse> restore(@PathVariable("permission") String name) {

        return ApiResponse.<PermissionResponse>builder()
                .message("Permission " + name + " has been restored successfully")
                .result(permissionService.restore(name))
                .build();
    }
}
