package com.vannam.auth_service.controller;


import com.vannam.auth_service.dto.request.PermissionRequest;
import com.vannam.auth_service.dto.request.RoleRequest;
import com.vannam.auth_service.dto.response.ApiResponse;
import com.vannam.auth_service.dto.response.PermissionResponse;
import com.vannam.auth_service.dto.response.RoleResponse;
import com.vannam.auth_service.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class RoleController {

    RoleService roleService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.createRole(request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    ApiResponse<RoleResponse> getById(@PathVariable("id") Long id) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.getById(id))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    ApiResponse<RoleResponse> update(@PathVariable("id") Long id,@RequestBody RoleRequest update) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.updateRole(id,update))
                .build();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    ApiResponse<String> deleteById(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ApiResponse.<String>builder()
                .result("Role has been deleted")
                .build();
    }
}
