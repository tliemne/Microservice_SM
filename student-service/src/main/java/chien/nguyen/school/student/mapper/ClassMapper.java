package chien.nguyen.school.student.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import chien.nguyen.school.student.dtos.request.ClassRequest;
import chien.nguyen.school.student.dtos.response.ClassResponse;
import chien.nguyen.school.student.model.ClassRoom;

@Mapper(componentModel = "spring")
public interface ClassMapper {

    ClassRoom toEntity(ClassRequest request);

    ClassResponse toResponse(ClassRoom c);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(ClassRequest request, @MappingTarget ClassRoom c);
}

