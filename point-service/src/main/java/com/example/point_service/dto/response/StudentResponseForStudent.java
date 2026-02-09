package com.example.point_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StudentResponse for STUDENT role (point-service)
 * - Only shows basic student info
 * - ❌ NO schoolId, NO classId, NO internal ID
 * - Prevents ID Enumeration Attack
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseForStudent {
    private String name;
    private String email;
    private String phone;
    private String address;
    // ❌ NOT included: id, classId, school, schoolId
}
