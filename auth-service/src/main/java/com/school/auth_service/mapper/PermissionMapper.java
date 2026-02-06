package com.school.auth_service.mapper;

import com.school.auth_service.dto.request.PermissionRequest;
import com.school.auth_service.dto.response.PermissionResponse;
import com.school.auth_service.model.Permission;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePermission(@MappingTarget Permission permission, PermissionRequest request);
}