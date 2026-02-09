package com.example.point_service.dto.response;

import com.example.point_service.dto.SubjectDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointResponseForStudent {
    private SubjectDTO subject;
    private Double score;
    private String semester;

}
