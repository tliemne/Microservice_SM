package com.school.student_service.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.school.common_library.dto.UserInternalResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {
    @JsonIgnore
    String id;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    SchoolResponse school;
    ClassesResponse classes;
    UserInternalResponse user;
    boolean deleted;
    LocalDateTime createdAt;
}
