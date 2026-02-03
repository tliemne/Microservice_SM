package com.school.student_service.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.school.student_service.enums.SchoolStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolResponse {
    @JsonIgnore
    String id;
    String name;
    String code;
    String address;
    String email;
    boolean deleted;
    SchoolStatus status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}