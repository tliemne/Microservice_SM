package com.school.auth_service.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInternalResponse {
    String id;
    String username;
    String password;
    Set<String> roles;
}
