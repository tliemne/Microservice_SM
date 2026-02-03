package com.school.student_service.mapper;

import com.school.student_service.dto.request.ClassesRequest;
import com.school.student_service.dto.response.ClassesResponse;
import com.school.student_service.model.Classes;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClassesMapper {
    @Mapping(target = "school.code", source = "schoolCode")
    Classes toEntity(ClassesRequest request);

    ClassesResponse toResponse(Classes classes);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "school", ignore = true)
    void updateEntity(ClassesRequest request, @MappingTarget Classes classes);
}
