package com.vannam.auth_service.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vannam.auth_service.entity.Permission;
import com.vannam.auth_service.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequest {

    String name;
    String description;
    Set<String> permissions;
}
