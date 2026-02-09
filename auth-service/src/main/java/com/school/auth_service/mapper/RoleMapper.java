package com.school.auth_service.mapper;

import com.school.auth_service.dto.request.RoleRequest;
import com.school.auth_service.dto.response.RoleResponse;
import com.school.auth_service.model.Role;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}