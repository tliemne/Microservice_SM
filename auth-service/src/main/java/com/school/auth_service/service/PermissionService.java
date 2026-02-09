package com.school.auth_service.service;

import com.school.common_library.exception.*;
import com.school.auth_service.dto.request.PermissionRequest;
import com.school.auth_service.dto.response.PermissionResponse;
import com.school.auth_service.mapper.PermissionMapper;
import com.school.auth_service.model.Permission;
import com.school.auth_service.repository.PermissionRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;


    public PermissionResponse create(PermissionRequest request) {
        if (permissionRepository.existsById(request.getName())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }

        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);

        log.info("Permission {} created successfully", permission.getName());
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    public PermissionResponse update(String name, PermissionRequest request) {
        Permission permission = permissionRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));

        permissionMapper.updatePermission(permission, request);

        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    public PermissionResponse delete(String name) {
        Permission permission = permissionRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        permission.setDeleted(true);
        permissionRepository.save(permission);
        log.warn("Permission {} has been soft deleted", name);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    public PermissionResponse restore(String name) {
        Permission permission = permissionRepository.findByNameAndDeletedTrue(name)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));

        permission.setDeleted(false);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

}
