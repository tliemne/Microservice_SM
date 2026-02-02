package com.school.user_service.mapper;

import com.school.user_service.dto.request.PermissionRequest;
import com.school.user_service.dto.response.PermissionResponse;
import com.school.user_service.model.Permission;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePermission(@MappingTarget Permission permission, PermissionRequest request);
}