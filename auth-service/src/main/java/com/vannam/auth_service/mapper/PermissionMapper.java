package com.vannam.auth_service.mapper;

import com.vannam.auth_service.dto.request.PermissionRequest;
import com.vannam.auth_service.dto.response.PermissionResponse;
import com.vannam.auth_service.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request);

    void update(PermissionRequest permissionRequest,@MappingTarget Permission permission);
    PermissionResponse toPermissionResponse(Permission permission);
}
