package chien.nguyen.school.student.service.impl;

import chien.nguyen.school.student.config.SecurityService;
import chien.nguyen.school.student.dtos.request.StudentRequest;
import chien.nguyen.school.student.dtos.response.StudentResponse;
import chien.nguyen.school.student.mapper.StudentMapper;
import chien.nguyen.school.student.model.ClassRoom;
import chien.nguyen.school.student.model.School;
import chien.nguyen.school.student.model.Student;
import chien.nguyen.school.student.repository.ClassRepository;
import chien.nguyen.school.student.repository.SchoolRepository;
import chien.nguyen.school.student.repository.StudentRepository;
import chien.nguyen.school.student.repository.client.AuthClient;
import chien.nguyen.school.student.service.StudentService;
import com.school.common_library.exception.AppException;
import com.school.common_library.exception.ErrorCode;
import com.school.common_library.security.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;
  private final SchoolRepository schoolRepository;
  private final ClassRepository classRepository;
  private final StudentMapper studentMapper;
  private final AuthClient authClient;
  private final SecurityService auth;

  @Override
  public List<StudentResponse> getStudents() {

    UserContext user = UserContext.getCurrentUser();
    if (user == null) {
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

    List<Student> students;

    if (user.hasRole("ROLE_ADMIN")) {
      students = studentRepository.findAll();
    } else if (user.hasRole("ROLE_SCHOOL_MANAGER")) {
      students = studentRepository.findBySchool_Id(user.getSchoolId());
    } else {
      students = studentRepository.findById(user.getStudentId())
              .map(List::of)
              .orElse(List.of());
    }

    return students.stream()
            .map(studentMapper::toResponse)
            .toList();
  }

  @Override
  public StudentResponse getStudentById(Long id) {

    Student student = studentRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXISTED));

    if (!canAccess(student)) {
      throw new AppException(ErrorCode.UNAUTHORIZED);
    }

    return studentMapper.toResponse(student);
  }

  @Override
  public StudentResponse createStudent(StudentRequest request) {
    boolean userExist;
    try {
      userExist = authClient.checkUserExists(request.getUserId());
    } catch (Exception e) {
      log.error("Lỗi kết nối Auth-Service khi check userId {}: {}", request.getUserId(), e.getMessage());
      throw new AppException(ErrorCode.AUTH_SERVICE_UNAVAILABLE);
    }

    if (!userExist) {
      throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }
    UserContext user = UserContext.getCurrentUser();
    if (user == null) {
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

    Student student = studentMapper.toEntity(request);
    School school;

    if (user.hasRole("ROLE_ADMIN")) {

      if (request.getSchoolId() == null) {
        throw new AppException(ErrorCode.FIELD_REQUIRED);
      }

      school = schoolRepository.findById(request.getSchoolId())
              .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));

    } else {

      if (!user.hasRole("ROLE_SCHOOL_MANAGER")) {
        throw new AppException(ErrorCode.UNAUTHORIZED);
      }

      school = schoolRepository.findById(user.getSchoolId())
              .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));
    }

    if (request.getClassRoomId() == null) {
      throw new AppException(ErrorCode.FIELD_REQUIRED);
    }

    ClassRoom classRoom = classRepository.findById(request.getClassRoomId())
            .orElseThrow(() -> new AppException(ErrorCode.CLASSES_NOT_EXISTED));

    if (!Objects.equals(classRoom.getSchool().getId(), school.getId())) {
      throw new AppException(ErrorCode.INVALID_KEY);
    }

    student.setSchool(school);
    student.setClassRoom(classRoom);

    return studentMapper.toResponse(studentRepository.save(student));
  }

  @Override
  public StudentResponse updateStudent(Long id, StudentRequest request) {

    Student student = studentRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXISTED));

    if (!canAccess(student)) {
      throw new AppException(ErrorCode.UNAUTHORIZED);
    }

    studentMapper.updateEntity(request, student);
    return studentMapper.toResponse(studentRepository.save(student));
  }

  @Override
  public void deleteStudent(Long id) {

    Student student = studentRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXISTED));

    if (!canAccess(student)) {
      throw new AppException(ErrorCode.UNAUTHORIZED);
    }

    studentRepository.delete(student);
  }

  private boolean canAccess(Student student) {

    UserContext user = UserContext.getCurrentUser();

    if (user.hasRole("ROLE_ADMIN")) return true;

    if (user.hasRole("ROLE_SCHOOL_MANAGER")) {
      return Objects.equals(student.getSchool().getId(), user.getSchoolId());
    }

    return Objects.equals(student.getId(), user.getStudentId());
  }
}
