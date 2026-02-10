package chien.nguyen.school.student.mapper;

import chien.nguyen.school.student.dtos.request.StudentRequest;
import chien.nguyen.school.student.dtos.response.StudentResponse;
import chien.nguyen.school.student.model.Student;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-10T09:51:42+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public Student toEntity(StudentRequest request) {
        if ( request == null ) {
            return null;
        }

        Student student = new Student();

        student.setFullName( request.getFullName() );
        student.setStudentCode( request.getStudentCode() );
        student.setEmail( request.getEmail() );
        student.setPhone( request.getPhone() );
        student.setGender( request.getGender() );

        return student;
    }

    @Override
    public StudentResponse toResponse(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentResponse.StudentResponseBuilder studentResponse = StudentResponse.builder();

        studentResponse.id( student.getId() );
        studentResponse.fullName( student.getFullName() );
        studentResponse.studentCode( student.getStudentCode() );
        studentResponse.email( student.getEmail() );
        studentResponse.phone( student.getPhone() );
        studentResponse.gender( student.getGender() );

        return studentResponse.build();
    }

    @Override
    public void updateEntity(StudentRequest request, Student student) {
        if ( request == null ) {
            return;
        }

        if ( request.getFullName() != null ) {
            student.setFullName( request.getFullName() );
        }
        if ( request.getStudentCode() != null ) {
            student.setStudentCode( request.getStudentCode() );
        }
        if ( request.getEmail() != null ) {
            student.setEmail( request.getEmail() );
        }
        if ( request.getPhone() != null ) {
            student.setPhone( request.getPhone() );
        }
        if ( request.getGender() != null ) {
            student.setGender( request.getGender() );
        }
    }
}
