package com.vannam.auth_service.controller;

import com.vannam.auth_service.dto.request.PermissionRequest;
import com.vannam.auth_service.dto.response.ApiResponse;
import com.vannam.auth_service.dto.response.PermissionResponse;
import com.vannam.auth_service.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class PermissionController {


    PermissionService permissionService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.createPermission(request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    ApiResponse<PermissionResponse> getById(@PathVariable("id") Long id) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.getById(id))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    ApiResponse<PermissionResponse> update(@PathVariable("id") Long id,@RequestBody PermissionRequest update) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.updatePermission(id,update))
                .build();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    ApiResponse<String> deleteById(@PathVariable("id") Long id) {
        permissionService.deleteById(id);
        return ApiResponse.<String>builder()
                .result("Permission has been deleted")
                .build();
    }
}
