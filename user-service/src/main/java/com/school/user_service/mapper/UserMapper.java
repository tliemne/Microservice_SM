package com.school.user_service.mapper;

import com.school.user_service.dto.request.UserCreationRequest;
import com.school.user_service.dto.request.UserUpdateRequest;
import com.school.user_service.dto.response.UserResponse;
import com.school.user_service.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
