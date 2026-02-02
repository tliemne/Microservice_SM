package com.example.point_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePointRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "School ID is required")
    private Long schoolId;   // <-- thêm

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotNull(message = "Score is required")
    @Min(value = 0, message = "Score must be at least 0")
    @Max(value = 100, message = "Score cannot exceed 100")
    private Double score;

    @NotBlank(message = "Semester is required")
    private String semester;
}


