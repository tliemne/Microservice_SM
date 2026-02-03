package com.school.student_service.dto.request;

import com.school.student_service.enums.SchoolStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SchoolRequest {
    String name;
    String code;
    String address;
    String email;
}
