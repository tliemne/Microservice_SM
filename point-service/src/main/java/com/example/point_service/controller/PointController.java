package com.example.point_service.controller;

import com.school.common_library.ApiResponse;

import com.example.point_service.dto.request.CreatePointRequest;
import com.example.point_service.dto.request.UpdatePointRequest;
import com.example.point_service.dto.response.PointResponse;
import com.example.point_service.dto.response.PointResponseMapper;
import com.school.common_library.security.UserContext;
import com.example.point_service.service.PointService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
@Slf4j
public class PointController {

    private final PointService pointService;
    private final PointResponseMapper pointResponseMapper;

    @PostMapping
<<<<<<< HEAD
//    @PreAuthorize("hasAnyRole('ADMIN','SCHOOL_MANAGER')")
=======
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
    public ResponseEntity<ApiResponse<PointResponse>> createPoint(
            @Valid @RequestBody CreatePointRequest request) {

        log.info("Creating point for student {}", request.getStudentId());

        PointResponse response = pointService.createPoint(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Point created successfully", response));
    }
<<<<<<< HEAD
    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN','SCHOOL_MANAGER','STUDENT')")
=======


    @GetMapping("/{id}")
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
    public ResponseEntity<ApiResponse<Object>> getById(@PathVariable Long id) {

        log.info("Get point {}", id);

        UserContext ctx = UserContext.getCurrentUser();

        PointResponse response =
                pointService.getPointByIdWithPermission(id, ctx);

        Object mapped = pointResponseMapper.map(response);

        return ResponseEntity.ok(ApiResponse.success(mapped));
    }


<<<<<<< HEAD

    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN','SCHOOL_MANAGER')")
=======
    @GetMapping
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
    public ResponseEntity<ApiResponse<List<Object>>> getAll() {

        log.info("Get all points");

        UserContext ctx = UserContext.getCurrentUser();

        List<PointResponse> list =
                pointService.getAllPoints(ctx);

        var mapped = list.stream()
                .map(pointResponseMapper::map)
                .toList();

        return ResponseEntity.ok(ApiResponse.success(mapped));
    }


<<<<<<< HEAD
    @GetMapping("/me")
//    @PreAuthorize("hasRole('STUDENT')")
=======

    @GetMapping("/me")
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
    public ResponseEntity<ApiResponse<List<Object>>> getMyPoints() {

        log.info("Get my points");

        UserContext ctx = UserContext.getCurrentUser();

        List<PointResponse> list =
                pointService.getMyPoints(ctx);

        var mapped = list.stream()
                .map(pointResponseMapper::map)
                .toList();

        return ResponseEntity.ok(ApiResponse.success(mapped));
    }

<<<<<<< HEAD
    @GetMapping("/student/{studentId}")
//    @PreAuthorize("hasAnyRole('ADMIN','SCHOOL_MANAGER','STUDENT')")
=======


    @GetMapping("/student/{studentId}")
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
    public ResponseEntity<ApiResponse<List<Object>>> getByStudent(
            @PathVariable Long studentId) {

        log.info("Get points of student {}", studentId);

        UserContext ctx = UserContext.getCurrentUser();

        List<PointResponse> list =
                pointService.getPointsByStudentWithPermission(studentId, ctx);

        var mapped = list.stream()
                .map(pointResponseMapper::map)
                .toList();

        return ResponseEntity.ok(ApiResponse.success(mapped));
    }


<<<<<<< HEAD
    @GetMapping("/student/{studentId}/semester/{semester}")
//    @PreAuthorize("hasAnyRole('ADMIN','SCHOOL_MANAGER','STUDENT')")
=======

    @GetMapping("/student/{studentId}/semester/{semester}")
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
    public ResponseEntity<ApiResponse<List<Object>>> getByStudentAndSemester(
            @PathVariable Long studentId,
            @PathVariable String semester) {

        log.info("Get points of student {} semester {}", studentId, semester);

        UserContext ctx = UserContext.getCurrentUser();

        List<PointResponse> list =
                pointService.getPointsByStudentAndSemesterWithPermission(
                        studentId, semester, ctx);

        var mapped = list.stream()
                .map(pointResponseMapper::map)
                .toList();

        return ResponseEntity.ok(ApiResponse.success(mapped));
    }



    @PutMapping("/{id}")
<<<<<<< HEAD
//    @PreAuthorize("hasAnyRole('ADMIN','SCHOOL_MANAGER')")
=======
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
    public ResponseEntity<ApiResponse<PointResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePointRequest request) {

        log.info("Update point {}", id);

        UserContext ctx = UserContext.getCurrentUser();

        PointResponse response =
                pointService.updatePointWithPermission(id, request, ctx);

        return ResponseEntity.ok(
                ApiResponse.success("Point updated successfully", response)
        );
    }

<<<<<<< HEAD
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN','SCHOOL_MANAGER')")
=======

    @DeleteMapping("/{id}")
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {

        log.info("Delete point {}", id);

        UserContext ctx = UserContext.getCurrentUser();

        pointService.deletePointWithPermission(id, ctx);

        return ResponseEntity.ok(
                ApiResponse.success("Point deleted successfully", null)
        );
    }


<<<<<<< HEAD

    @DeleteMapping("/student/{studentId}")
//    @PreAuthorize("permitAll()")
=======
    @DeleteMapping("/student/{studentId}")
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
    public ResponseEntity<ApiResponse<Void>> deleteByStudent(
            @PathVariable Long studentId) {

        log.info("Internal delete points of student {}", studentId);

        pointService.deletePointsByStudentId(studentId);

        return ResponseEntity.ok(
                ApiResponse.success("All points deleted", null)
        );
    }

}
