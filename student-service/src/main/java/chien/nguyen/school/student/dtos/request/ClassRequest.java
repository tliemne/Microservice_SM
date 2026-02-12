package chien.nguyen.school.student.dtos.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassRequest {

    @NotBlank(message = "Class name is required")
    @Size(max = 100)
    private String name;

    @NotNull(message = "schoolId is required")
    private Long schoolId;
}
