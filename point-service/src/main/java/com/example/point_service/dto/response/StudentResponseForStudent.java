package com.example.point_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseForStudent {
    private String name;
    private String email;
    private String phone;
    private String address;

}
