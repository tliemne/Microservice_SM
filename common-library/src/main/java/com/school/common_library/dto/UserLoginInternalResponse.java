package com.school.common_library.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginInternalResponse {
    String id;
    String username;
    String password;
    Set<String> roles;
}