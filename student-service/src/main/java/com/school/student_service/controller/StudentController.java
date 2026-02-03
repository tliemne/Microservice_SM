package com.school.student_service.controller;

import com.school.common_library.ApiResponse;
import com.school.student_service.dto.request.StudentRequest;
import com.school.student_service.dto.response.StudentResponse;
import com.school.student_service.mapper.StudentMapper;
import com.school.student_service.model.*;
import com.school.student_service.repository.*;
import com.school.student_service.service.StudentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentController {
    StudentService studentService;
    StudentMapper studentMapper;
    StudentRepository studentRepository;
    SchoolRepository schoolRepository;
    ClassesRepository classRepository;

    @GetMapping
    public ApiResponse<List<StudentResponse>> getAllStudents() {
        return ApiResponse.<List<StudentResponse>>builder()
                .result(studentService.getAll())
                .build();
    }

    @GetMapping("/{code}")
    public ApiResponse<StudentResponse> getByCode(@PathVariable String code) {
        return ApiResponse.<StudentResponse>builder()
                .result(studentService.getByCode(code))
                .build();
    }

    @PostMapping
    public ApiResponse<StudentResponse> addStudent(@RequestBody StudentRequest studentRequest) {
        return ApiResponse.<StudentResponse>builder()
                .result(studentService.create(studentRequest))
                .build();
    }

    @PutMapping("/{code}")
    public ApiResponse<StudentResponse> updateStudent(@PathVariable String code,@RequestBody StudentRequest studentRequest) {
        return ApiResponse.<StudentResponse>builder()
                .result(studentService.update(code,studentRequest))
                .build();
    }

    @DeleteMapping("/{code}")
    public ApiResponse<StudentResponse> delete(@PathVariable String code) {
        return ApiResponse.<StudentResponse>builder()
                .result(studentService.delete(code))
                .build();
    }

    @PutMapping("/restore/{code}")
    public ApiResponse<StudentResponse> restore(@PathVariable String code) {
        return ApiResponse.<StudentResponse>builder()
                .result(studentService.restore(code))
                .build();
    }
}
