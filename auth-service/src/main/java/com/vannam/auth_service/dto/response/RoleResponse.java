package com.vannam.auth_service.dto.response;

import com.vannam.auth_service.entity.Permission;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {

    Long id;
    String name;
    String description;
    Set<Permission> permissions;
}

