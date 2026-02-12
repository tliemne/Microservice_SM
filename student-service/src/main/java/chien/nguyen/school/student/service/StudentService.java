package chien.nguyen.school.student.service;

import org.springframework.security.core.Authentication;
import chien.nguyen.school.student.dtos.request.StudentRequest;
import chien.nguyen.school.student.dtos.response.StudentResponse;

import java.util.List;

public interface StudentService {

    List<StudentResponse> getStudents();

    StudentResponse getStudentById(Long id);

    StudentResponse createStudent(StudentRequest request);

    StudentResponse updateStudent(Long id, StudentRequest request);

    void deleteStudent(Long id);
}

