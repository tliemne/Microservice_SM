package chien.nguyen.school.student.dtos.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolResponse {

    private Long id;
    private String name;
    private String address;
    private String phone;
}
