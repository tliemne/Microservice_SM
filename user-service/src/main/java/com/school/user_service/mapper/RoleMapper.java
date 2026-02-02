package com.school.user_service.mapper;

import com.school.user_service.dto.request.RoleRequest;
import com.school.user_service.dto.response.RoleResponse;
import com.school.user_service.model.Role;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}