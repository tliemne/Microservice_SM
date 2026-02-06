package com.vannam.auth_service.dto.request;

import com.vannam.auth_service.enums.DataScope;
import com.vannam.auth_service.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    String username;
    String password;
    Status status;
    String roleName;
    Set<DataScope> dataScopes;
}
