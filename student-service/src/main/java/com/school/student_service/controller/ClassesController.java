package com.school.student_service.controller;

import com.school.common_library.ApiResponse;
import com.school.student_service.dto.request.ClassesRequest;
import com.school.student_service.dto.response.ClassesResponse;
import com.school.student_service.repository.ClassesRepository;
import com.school.student_service.service.ClassesService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassesController {
    ClassesService classesService;
    ClassesRepository classesRepository;

    @GetMapping
    public ApiResponse<List<ClassesResponse>> getAllClasses() {
        return ApiResponse.<List<ClassesResponse>>builder()
                .result(classesService.getAll())
                .build();
    }

    @GetMapping("/{code}")
    public ApiResponse<ClassesResponse> getAllClasses(@PathVariable String code) {
        return ApiResponse.<ClassesResponse>builder()
                .result(classesService.getByCode(code))
                .build();
    }

    @PostMapping
    public ApiResponse<ClassesResponse> addClass(@RequestBody ClassesRequest classesRequest) {
        return ApiResponse.<ClassesResponse>builder()
                .result(classesService.create(classesRequest))
                .build();
    }

    @PutMapping("/{code}")
    public ApiResponse<ClassesResponse> updateClass(@PathVariable String code, @RequestBody ClassesRequest classesRequest) {
        return ApiResponse.<ClassesResponse>builder()
                .result(classesService.update(code, classesRequest))
                .build();
    }

    @DeleteMapping("/{code}")
    public ApiResponse<ClassesResponse> delete(@PathVariable String code) {
        return ApiResponse.<ClassesResponse>builder()
                .result(classesService.delete(code))
                .build();
    }

    @PutMapping("/restore/{code}")
    public ApiResponse<ClassesResponse> restore(@PathVariable String code) {
        return ApiResponse.<ClassesResponse>builder()
                .result(classesService.restore(code))
                .build();
    }
}
