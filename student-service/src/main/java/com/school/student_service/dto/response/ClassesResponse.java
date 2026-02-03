package com.school.student_service.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassesResponse {
    @JsonIgnore
    String id;
    String name;
    String code;
    SchoolResponse school;
    boolean deleted;
    LocalDateTime createdAt;
}
