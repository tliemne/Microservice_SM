package com.example.point_service.dto.response;

import com.example.point_service.dto.SubjectDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointResponse {
    private Long id;

    @JsonProperty("studentId")
    private Long studentId;

    @JsonProperty("subject")
    private SubjectDTO subject;

    private Double score;

    private String semester;
}
