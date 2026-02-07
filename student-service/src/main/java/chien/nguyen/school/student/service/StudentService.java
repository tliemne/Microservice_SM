package chien.nguyen.school.student.service;

import org.springframework.security.core.Authentication;
import chien.nguyen.school.student.dtos.request.StudentRequest;
import chien.nguyen.school.student.dtos.response.StudentResponse;

import java.util.List;

public interface StudentService {

    List<StudentResponse> getStudents(Authentication authentication);

    StudentResponse getStudentById(Authentication authentication, Long id);

    StudentResponse createStudent(Authentication authentication, StudentRequest request);

    StudentResponse updateStudent(Authentication authentication, Long id, StudentRequest request);

    void deleteStudent(Authentication authentication, Long id);
}
