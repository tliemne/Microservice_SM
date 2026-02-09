package com.example.point_service.mapper;

import com.example.point_service.dto.SubjectDTO;
import com.example.point_service.dto.response.PointResponse;
import com.example.point_service.entity.Point;
import com.example.point_service.entity.Subject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PointMapper {
    PointResponse pointToPointResponse(Point point);

    SubjectDTO subjectToSubjectDTO(Subject subject);
}
