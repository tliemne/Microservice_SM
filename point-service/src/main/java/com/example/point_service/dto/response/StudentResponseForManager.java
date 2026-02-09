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
public class StudentResponseForManager {
    private Long id;
    private String name;
    private String email;
    private Long classId;
    private String phone;
    private String address;

}
