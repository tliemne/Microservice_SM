package chien.nguyen.school.student.dtos.request;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StudentRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 100)
    private String fullName;

    @NotBlank(message = "Student code is required")
    @Size(max = 50)
    private String studentCode;

    @Email(message = "Email is invalid")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(
            regexp = "^[0-9]{9,11}$",
            message = "Phone must be 9–11 digits"
    )
    private String phone;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotNull(message = "schoolId is required")
    private Long schoolId;

    @NotNull(message = "classRoomId is required")
    private Long classRoomId;

    private String userId;
}
