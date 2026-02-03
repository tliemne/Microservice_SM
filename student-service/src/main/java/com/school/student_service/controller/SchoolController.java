package com.school.student_service.controller;

import com.school.common_library.ApiResponse;
import com.school.student_service.dto.request.SchoolRequest;
import com.school.student_service.dto.response.SchoolResponse;
import com.school.student_service.service.SchoolService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schools")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SchoolController {
    SchoolService schoolService;

    @GetMapping
    public ApiResponse<List<SchoolResponse>> getAllSchools() {
        return ApiResponse.<List<SchoolResponse>>builder()
                .result(schoolService.getAll())
                .build();
    }

    @PostMapping
    public ApiResponse<SchoolResponse> create(@RequestBody SchoolRequest request){
        return ApiResponse.<SchoolResponse>builder()
                .result(schoolService.create(request))
                .build();
    }

    @GetMapping("/{code}")
    public ApiResponse<SchoolResponse> getSchoolByCode(@PathVariable String code){
        return ApiResponse.<SchoolResponse>builder()
                .result(schoolService.getByCode(code))
                .build();
    }

    @PutMapping("/{code}")
    public ApiResponse<SchoolResponse> update(@PathVariable String code, @RequestBody SchoolRequest request){
        return ApiResponse.<SchoolResponse>builder()
                .result(schoolService.update(code, request))
                .build();
    }

    @DeleteMapping("/{code}")
    public ApiResponse<SchoolResponse> delete(@PathVariable String code){
        return ApiResponse.<SchoolResponse>builder()
                .message("Deleted")
                .result(schoolService.delete(code))
                .build();
    }

    @PutMapping("/restore/{code}")
    public ApiResponse<SchoolResponse> restore(@PathVariable String code){
        return ApiResponse.<SchoolResponse>builder()
                .message("Restored")
                .result(schoolService.restore(code))
                .build();
    }

}
