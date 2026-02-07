package chien.nguyen.school.student.mapper;

import chien.nguyen.school.student.dtos.request.ClassRequest;
import chien.nguyen.school.student.dtos.response.ClassResponse;
import chien.nguyen.school.student.model.ClassRoom;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-06T09:38:37+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class ClassMapperImpl implements ClassMapper {

    @Override
    public ClassRoom toEntity(ClassRequest request) {
        if ( request == null ) {
            return null;
        }

        ClassRoom classRoom = new ClassRoom();

        classRoom.setName( request.getName() );

        return classRoom;
    }

    @Override
    public ClassResponse toResponse(ClassRoom c) {
        if ( c == null ) {
            return null;
        }

        ClassResponse.ClassResponseBuilder classResponse = ClassResponse.builder();

        classResponse.id( c.getId() );
        classResponse.name( c.getName() );

        return classResponse.build();
    }

    @Override
    public void updateEntity(ClassRequest request, ClassRoom c) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            c.setName( request.getName() );
        }
    }
}
