package chien.nguyen.school.student.mapper;

import org.mapstruct.*;
import chien.nguyen.school.student.dtos.request.SchoolRequest;
import chien.nguyen.school.student.dtos.response.SchoolResponse;
import chien.nguyen.school.student.model.School;

@Mapper(componentModel = "spring")
public interface SchoolMapper {

    School toEntity(SchoolRequest request);

    SchoolResponse toResponse(School school);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(SchoolRequest request, @MappingTarget School school);
}
