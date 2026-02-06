package com.vannam.auth_service.service;


import com.vannam.auth_service.dto.request.PermissionRequest;
import com.vannam.auth_service.dto.response.PermissionResponse;
import com.vannam.auth_service.entity.Permission;
import com.vannam.auth_service.exception.AppException;
import com.vannam.auth_service.exception.ErrorCode;
import com.vannam.auth_service.mapper.PermissionMapper;
import com.vannam.auth_service.repository.PermissionRepository;
import com.vannam.auth_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class PermissionService {

    PermissionMapper permissionMapper;
    PermissionRepository permissionRepository;

    public PermissionResponse createPermission(PermissionRequest request){
        if(permissionRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        Permission permission = permissionMapper.toPermission(request);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }


    public PermissionResponse updatePermission(Long id,PermissionRequest update){

        Permission permission=permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        permissionMapper.update(update,permission);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);

    }

    public List<PermissionResponse> getAll() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }


    public PermissionResponse getById(Long id) {
        return permissionMapper.toPermissionResponse(
                permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED)));
    }


    public void deleteById(Long id) {

        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));

        permissionRepository.delete(permission);
    }

}
