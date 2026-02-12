package chien.nguyen.school.student.mapper;

import chien.nguyen.school.student.dtos.request.SchoolRequest;
import chien.nguyen.school.student.dtos.response.SchoolResponse;
import chien.nguyen.school.student.model.School;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-11T10:14:39+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Microsoft)"
)
@Component
public class SchoolMapperImpl implements SchoolMapper {

    @Override
    public School toEntity(SchoolRequest request) {
        if ( request == null ) {
            return null;
        }

        School school = new School();

        school.setName( request.getName() );
        school.setAddress( request.getAddress() );
        school.setPhone( request.getPhone() );

        return school;
    }

    @Override
    public SchoolResponse toResponse(School school) {
        if ( school == null ) {
            return null;
        }

        SchoolResponse.SchoolResponseBuilder schoolResponse = SchoolResponse.builder();

        schoolResponse.id( school.getId() );
        schoolResponse.name( school.getName() );
        schoolResponse.address( school.getAddress() );
        schoolResponse.phone( school.getPhone() );

        return schoolResponse.build();
    }

    @Override
    public void updateEntity(SchoolRequest request, School school) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            school.setName( request.getName() );
        }
        if ( request.getAddress() != null ) {
            school.setAddress( request.getAddress() );
        }
        if ( request.getPhone() != null ) {
            school.setPhone( request.getPhone() );
        }
    }
}
