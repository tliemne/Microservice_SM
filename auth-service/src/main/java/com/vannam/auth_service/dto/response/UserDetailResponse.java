package com.vannam.auth_service.dto.response;


import com.vannam.auth_service.entity.Role;
import com.vannam.auth_service.enums.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailResponse {
    Long id;
    String username;
    String role;

}
