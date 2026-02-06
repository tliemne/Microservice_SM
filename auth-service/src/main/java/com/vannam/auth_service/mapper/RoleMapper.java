package com.vannam.auth_service.mapper;

import com.vannam.auth_service.dto.request.RoleRequest;
import com.vannam.auth_service.dto.response.RoleResponse;
import com.vannam.auth_service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {


    @Mapping(target = "permissions",ignore = true)
    Role toRole(RoleRequest request);

    @Mapping(target = "permissions",ignore = true)
    void update(RoleRequest request,@MappingTarget Role role);
    RoleResponse toRoleResponse(Role role);

}
