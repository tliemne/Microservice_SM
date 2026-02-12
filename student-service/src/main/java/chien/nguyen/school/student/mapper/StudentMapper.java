package chien.nguyen.school.student.mapper;

import org.mapstruct.*;
import chien.nguyen.school.student.dtos.request.StudentRequest;
import chien.nguyen.school.student.dtos.response.StudentResponse;
import chien.nguyen.school.student.model.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student toEntity(StudentRequest request);

    StudentResponse toResponse(Student student);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(StudentRequest request, @MappingTarget Student student);
}
