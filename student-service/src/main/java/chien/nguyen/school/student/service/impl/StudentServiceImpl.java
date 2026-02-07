package chien.nguyen.school.student.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import chien.nguyen.school.student.dtos.request.StudentRequest;
import chien.nguyen.school.student.dtos.response.StudentResponse;
import chien.nguyen.school.student.mapper.StudentMapper;
import chien.nguyen.school.student.model.ClassRoom;
import chien.nguyen.school.student.model.School;
import chien.nguyen.school.student.model.Student;
import chien.nguyen.school.student.repository.ClassRepository;
import chien.nguyen.school.student.repository.SchoolRepository;
import chien.nguyen.school.student.repository.StudentRepository;
import chien.nguyen.school.student.service.StudentService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;
  private final SchoolRepository schoolRepository;
  private final ClassRepository classRepository;
  private final StudentMapper studentMapper;

  @Override
  public List<StudentResponse> getStudents(Authentication authentication) {

    Map<?, ?> details = (Map<?, ?>) authentication.getDetails();
    String scope = String.valueOf(details.get("dataScope"));

    List<Student> students;

    switch (scope) {
      case "ALL" -> students = studentRepository.findAll();

      case "SCHOOL" -> {
        Long schoolId = Long.valueOf(details.get("schoolId").toString());
        students = studentRepository.findBySchool_Id(schoolId);
      }

      default -> {
        Long studentId = Long.valueOf(details.get("studentId").toString());
        students = studentRepository.findById(studentId)
                .map(List::of)
                .orElse(List.of());
      }
    }

    return students.stream()
            .map(studentMapper::toResponse)
            .toList();
  }

  @Override
  public StudentResponse getStudentById(Authentication authentication, Long id) {

    Student student = studentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    return studentMapper.toResponse(student);
  }

  @Override
  public StudentResponse createStudent(Authentication authentication, StudentRequest request) {

    Student student = studentMapper.toEntity(request);
    School school;

    if (hasRole(authentication, "ADMIN")) {

      if (request.getSchoolId() == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "schoolId is required");
      }

      school = schoolRepository.findById(request.getSchoolId())
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "School not found"));

    } else {

      Map<?, ?> details = (Map<?, ?>) authentication.getDetails();
      String scope = String.valueOf(details.get("dataScope"));

      if (!"SCHOOL".equals(scope)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
      }

      Long schoolId = Long.valueOf(details.get("schoolId").toString());

      school = schoolRepository.findById(schoolId)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "School not found"));
    }

    if (request.getClassRoomId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "classRoomId is required");
    }

    ClassRoom classRoom = classRepository.findById(request.getClassRoomId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Class not found"));

    if (!classRoom.getSchool().getId().equals(school.getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Class does not belong to this school");
    }

    student.setSchool(school);
    student.setClassRoom(classRoom);

    return studentMapper.toResponse(studentRepository.save(student));
  }

  @Override
  public StudentResponse updateStudent(Authentication authentication, Long id, StudentRequest request) {

    Student student = studentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (!canAccess(authentication, student)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    studentMapper.updateEntity(request, student);

    return studentMapper.toResponse(studentRepository.save(student));
  }

  @Override
  public void deleteStudent(Authentication authentication, Long id) {

    Student student = studentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (!canAccess(authentication, student)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    studentRepository.delete(student);
  }

  private boolean canAccess(Authentication authentication, Student student) {

    if (hasRole(authentication, "ADMIN")) return true;

    Map<?, ?> details = (Map<?, ?>) authentication.getDetails();
    String scope = String.valueOf(details.get("dataScope"));

    if ("SCHOOL".equals(scope)) {
      Long schoolId = Long.valueOf(details.get("schoolId").toString());
      return Objects.equals(student.getSchool().getId(), schoolId);
    }

    Long studentId = Long.valueOf(details.get("studentId").toString());
    return Objects.equals(student.getId(), studentId);
  }

  private boolean hasRole(Authentication authentication, String role) {
    return authentication.getAuthorities().stream()
            .anyMatch(r ->
                    r.getAuthority().equals(role) ||
                            r.getAuthority().equals("ROLE_" + role)
            );
  }
}
