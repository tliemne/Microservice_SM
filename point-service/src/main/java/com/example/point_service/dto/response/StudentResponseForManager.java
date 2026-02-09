package com.example.point_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StudentResponse for SCHOOL_MANAGER role (point-service)
 * - Shows: name, email, phone, address, classId
 * - ✅ INCLUDES classId (manager needs it)
 * - ❌ NO schoolId, NO school object (manager knows their school)
 * - Applies Least Privilege principle
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseForManager {
    private Long id;
    private String name;
    private String email;
    private Long classId;
    private String phone;
    private String address;
    // ❌ NOT included: schoolId, school
}
