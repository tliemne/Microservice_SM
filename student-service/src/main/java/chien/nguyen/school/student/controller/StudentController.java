package chien.nguyen.school.student.controller;

import chien.nguyen.school.student.dtos.request.StudentRequest;
import chien.nguyen.school.student.dtos.response.StudentResponse;
import chien.nguyen.school.student.model.Student;
import chien.nguyen.school.student.service.StudentService;
import com.school.common_library.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

  private final StudentService studentService;

//  @PreAuthorize("hasAuthority('STUDENT_VIEW')")
  @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponse>>> list() {
      return ResponseEntity.ok(
              ApiResponse.success(studentService.getStudents())
      );
    }

//  @PreAuthorize("hasAuthority('STUDENT_VIEW')")
  @GetMapping("/{id}")
  @PreAuthorize("@auth.isSelfOrAdmin(#id)")
  public ResponseEntity<ApiResponse<StudentResponse>> get(@PathVariable Long id) {
    return ResponseEntity.ok(
            ApiResponse.success(studentService.getStudentById(id))
    );
  }

//  @PreAuthorize("hasAuthority('STUDENT_CREATE')")
@PostMapping
public ResponseEntity<ApiResponse<StudentResponse>> create(@RequestBody StudentRequest request) {
    return ResponseEntity.ok(
            ApiResponse.success(studentService.createStudent(request))
    );
}

//  @PreAuthorize("hasAuthority('STUDENT_UPDATE')")
  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<StudentResponse>> update(
          @PathVariable Long id,
          @Valid @RequestBody StudentRequest request) {

    return ResponseEntity.ok(
            ApiResponse.success(studentService.updateStudent(id, request))
    );
  }

//  @PreAuthorize("hasAuthority('STUDENT_DELETE')")
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    studentService.deleteStudent(id);
    return ResponseEntity.ok(ApiResponse.success());
  }
}
