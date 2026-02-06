package com.vannam.auth_service.dto.response;

import com.vannam.auth_service.enums.DataScope;
import com.vannam.auth_service.entity.Role;
import com.vannam.auth_service.enums.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String username;
    Status status;
    Role role;
    Set<DataScope> dataScopes;
}
