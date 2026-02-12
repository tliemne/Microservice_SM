package com.school.auth_service.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignRoleRequest {
    String userId;
    String roleName;
    String schoolId;
}