package com.school.student_service.mapper;

import com.school.student_service.dto.request.SchoolRequest;
import com.school.student_service.dto.response.SchoolResponse;
import com.school.student_service.model.School;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
    School toEntity(SchoolRequest request);
    SchoolResponse toResponse(School school);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(SchoolRequest request, @MappingTarget School school);
}