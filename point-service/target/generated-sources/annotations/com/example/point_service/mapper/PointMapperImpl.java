package com.example.point_service.mapper;

import com.example.point_service.dto.SubjectDTO;
import com.example.point_service.dto.response.PointResponse;
import com.example.point_service.entity.Point;
import com.example.point_service.entity.Subject;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-09T11:09:29+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Microsoft)"
)
@Component
public class PointMapperImpl implements PointMapper {

    @Override
    public PointResponse pointToPointResponse(Point point) {
        if ( point == null ) {
            return null;
        }

        PointResponse.PointResponseBuilder pointResponse = PointResponse.builder();

        pointResponse.id( point.getId() );
        pointResponse.studentId( point.getStudentId() );
        pointResponse.subject( subjectToSubjectDTO( point.getSubject() ) );
        pointResponse.score( point.getScore() );
        pointResponse.semester( point.getSemester() );

        return pointResponse.build();
    }

    @Override
    public SubjectDTO subjectToSubjectDTO(Subject subject) {
        if ( subject == null ) {
            return null;
        }

        SubjectDTO.SubjectDTOBuilder subjectDTO = SubjectDTO.builder();

        subjectDTO.subjectCode( subject.getSubjectCode() );
        subjectDTO.subjectName( subject.getSubjectName() );
        subjectDTO.credits( subject.getCredits() );
        subjectDTO.description( subject.getDescription() );
        subjectDTO.isActive( subject.getIsActive() );

        return subjectDTO.build();
    }
}
