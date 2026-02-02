package com.school.user_service.dto.request;

import com.school.common_library.validator.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;

    @NotBlank(message = "FIELD_REQUIRED")
    private String firstName;

    @NotBlank(message = "FIELD_REQUIRED")
    private String lastName;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    private LocalDate birthday;

    Set<String> roles;
}
