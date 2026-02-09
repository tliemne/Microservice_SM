package com.example.point_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubjectDTO {
//    private Long id;
    private String subjectCode;
    private String subjectName;
    private Integer credits;
    private String description;
    private Boolean isActive;
}
