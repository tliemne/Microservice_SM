package chien.nguyen.school.student.dtos.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolRequest {

    @NotBlank(message = "School name is required")
    @Size(max = 150)
    private String name;

    @NotBlank(message = "Address is required")
    @Size(max = 255)
    private String address;

    @Pattern(
            regexp = "^[0-9]{9,11}$",
            message = "Phone must be 9–11 digits"
    )
    private String phone;
}
