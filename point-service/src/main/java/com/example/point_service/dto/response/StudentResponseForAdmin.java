package com.example.point_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseForAdmin {
    private Long id;
    private String name;
    private String email;
    private Long classId;
    private String phone;
    private String address;
    
    @JsonProperty("school")
    private SchoolInfo school;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SchoolInfo {
        private Long id;
        private String schoolName;
        private String schoolCode;
        private String description;
        private Boolean isActive;
    }
    public Long getSchoolId() {
        return school != null ? school.getId() : null;
    }
}
