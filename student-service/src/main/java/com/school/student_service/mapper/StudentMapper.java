package com.school.student_service.mapper;

import com.school.student_service.dto.request.StudentRequest;
import com.school.student_service.dto.response.StudentResponse;
import com.school.student_service.model.Student;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "school.code", source = "schoolCode")
    @Mapping(target = "clazz.code", source = "classCode")
    Student toEntity(StudentRequest request);

    StudentResponse toResponse(Student student);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "school", ignore = true)
    @Mapping(target = "clazz", ignore = true)
    void updateEntity(StudentRequest request, @MappingTarget Student student);
}
