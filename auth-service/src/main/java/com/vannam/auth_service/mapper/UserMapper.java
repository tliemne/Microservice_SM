package com.vannam.auth_service.mapper;

import com.vannam.auth_service.dto.request.UserRequest;
import com.vannam.auth_service.dto.response.UserDetailResponse;
import com.vannam.auth_service.dto.response.UserResponse;
import com.vannam.auth_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //phai tu map role vi Trong User là Role con trong UserRquest là roleId
    @Mapping(target = "role",ignore = true)
    @Mapping(target = "dataScopes",ignore = true)
    User toUser(UserRequest request);

    void update(UserRequest request,@MappingTarget User user);

    UserResponse toUserResponse(User user);

    @Mapping(target = "role", source = "role.name")
    UserDetailResponse toUserDetailResponse(User user);
}
