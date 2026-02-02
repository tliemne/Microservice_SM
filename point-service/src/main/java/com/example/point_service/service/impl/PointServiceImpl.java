package com.example.point_service.service.impl;

import com.example.point_service.client.StudentClient;
import com.example.point_service.dto.request.CreatePointRequest;
import com.example.point_service.dto.request.UpdatePointRequest;
import com.example.point_service.dto.response.PointResponse;
import com.example.point_service.dto.response.StudentResponse;
import com.example.point_service.entity.Point;
import com.example.point_service.entity.Subject;
import com.example.point_service.mapper.PointMapper;
import com.example.point_service.repository.PointRepository;
import com.example.point_service.repository.SubjectRepository;
import com.example.point_service.security.UserContext;
import com.example.point_service.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final SubjectRepository subjectRepository;
    private final PointMapper pointMapper;
    private final StudentClient studentClient;

    @Override
    public PointResponse createPoint(CreatePointRequest request) {

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() ->
                        new RuntimeException("Subject not found: " + request.getSubjectId())
                );

        Point point = Point.builder()
                .studentId(request.getStudentId())
                .subject(subject)
                .score(request.getScore())
                .semester(request.getSemester())
                .build();

        Point saved = pointRepository.save(point);

        return pointMapper.pointToPointResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PointResponse getPointById(Long id) {

        Point point = pointRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Point not found: " + id)
                );

        return pointMapper.pointToPointResponse(point);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PointResponse> getPointsByStudentId(Long studentId) {

        return pointRepository.findByStudentId(studentId)
                .stream()
                .map(pointMapper::pointToPointResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PointResponse> getPointsByStudentIdAndSemester(Long studentId, String semester) {

        return pointRepository
                .findByStudentIdAndSemester(studentId, semester)
                .stream()
                .map(pointMapper::pointToPointResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PointResponse> getAllPoints(UserContext ctx) {

        log.info("Get all points - user={}, role={}, schoolId={}",
                ctx.getUsername(), ctx.getRoles(), ctx.getSchoolId());

        List<Point> points = pointRepository.findAll();

        // ADMIN: xem hết
        if (ctx.isAdmin()) {
            return points.stream()
                    .map(pointMapper::pointToPointResponse)
                    .toList();
        }

        // SCHOOL MANAGER: lọc theo school
        if (ctx.isSchoolManager()) {

            String token = "Bearer " + ctx.getAuthToken();

            return points.stream()
                    .filter(p -> {

                        try {
                            StudentResponse student =
                                    studentClient.getStudent(p.getStudentId(), token);

                            if (student == null || student.getSchoolId() == null) {
                                return false;
                            }

                            return student.getSchoolId().equals(ctx.getSchoolId());

                        } catch (Exception e) {

                            log.error("Call student-service failed: {}", e.getMessage());
                            return false;
                        }

                    })
                    .map(pointMapper::pointToPointResponse)
                    .toList();
        }

        // STUDENT: chỉ xem của mình
        if (ctx.isStudent()) {
            return points.stream()
                    .filter(p -> p.getStudentId().equals(ctx.getStudentId()))
                    .map(pointMapper::pointToPointResponse)
                    .toList();
        }

        return List.of();
    }


    @Override
    public PointResponse updatePoint(Long id, UpdatePointRequest request) {

        Point point = pointRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Point not found: " + id)
                );

        if (request.getScore() != null) {
            point.setScore(request.getScore());
        }

        if (request.getSemester() != null) {
            point.setSemester(request.getSemester());
        }

        Point updated = pointRepository.save(point);

        return pointMapper.pointToPointResponse(updated);
    }

    @Override
    public void deletePoint(Long id) {

        if (!pointRepository.existsById(id)) {
            throw new RuntimeException("Point not found: " + id);
        }

        pointRepository.deleteById(id);
    }

    @Override
    public void deletePointsByStudentId(Long studentId) {

        List<Point> points = pointRepository.findByStudentId(studentId);

        if (!points.isEmpty()) {
            pointRepository.deleteAll(points);
        }
    }

    @Override
    public boolean canAccessPoint(Long pointId, UserContext ctx) {

        // ADMIN: full quyền
        if (ctx.isAdmin()) {
            return true;
        }

        // SM: cấm truy cập trực tiếp
        if (ctx.isSchoolManager()) {
            return false;
        }

        Point point = pointRepository.findById(pointId).orElse(null);

        if (point == null) {
            return false;
        }

        // STUDENT: chỉ xem điểm mình
        if (ctx.isStudent()) {
            return ctx.getStudentId() != null
                    && ctx.getStudentId().equals(point.getStudentId());
        }

        return false;
    }

    @Override
    public boolean canAccessStudentPoints(Long studentId, UserContext ctx) {

        if (ctx.isAdmin()) {
            return true;
        }

        // Student chỉ xem mình
        if (ctx.isStudent()) {
            return ctx.getStudentId() != null
                    && ctx.getStudentId().equals(studentId);
        }

        // School Manager kiểm tra schoolId
        if (ctx.isSchoolManager()) {

            try {
                String token = "Bearer " + ctx.getAuthToken();

                StudentResponse student =
                        studentClient.getStudent(studentId, token);

                if (student == null || student.getSchoolId() == null) {
                    return false;
                }

                return student.getSchoolId().equals(ctx.getSchoolId());

            } catch (Exception e) {
                return false;
            }
        }

        return false;
    }
    /* ================= PERMISSION WRAPPER ================= */


    @Override
    @Transactional(readOnly = true)
    public PointResponse getPointByIdWithPermission(Long id, UserContext ctx) {

        PointResponse response = getPointById(id);

        // Student chỉ xem của mình
        if (ctx.isStudent()) {
            if (!ctx.getStudentId().equals(response.getStudentId())) {
                throw new RuntimeException("Access denied");
            }
        }

        // School manager: check school
        if (ctx.isSchoolManager()) {

            try {
                String token = "Bearer " + ctx.getAuthToken();

                StudentResponse student =
                        studentClient.getStudent(response.getStudentId(), token);

                if (student == null
                        || !student.getSchoolId().equals(ctx.getSchoolId())) {

                    throw new RuntimeException("Access denied");
                }

            } catch (Exception e) {
                throw new RuntimeException("Access denied");
            }
        }

        return response;
    }


    /* ================= GET MY POINT ================= */

    @Override
    @Transactional(readOnly = true)
    public List<PointResponse> getMyPoints(UserContext ctx) {

        if (!ctx.isStudent()) {
            throw new RuntimeException("Only student can access");
        }

        if (ctx.getStudentId() == null) {
            throw new RuntimeException("Invalid token");
        }

        return getPointsByStudentId(ctx.getStudentId());
    }


    /* ================= GET BY STUDENT ================= */

    @Override
    @Transactional(readOnly = true)
    public List<PointResponse> getPointsByStudentWithPermission(
            Long studentId, UserContext ctx) {

        if (!canAccessStudentPoints(studentId, ctx)) {
            throw new RuntimeException("Access denied");
        }

        return getPointsByStudentId(studentId);
    }


    /* ================= GET BY STUDENT + SEMESTER ================= */

    @Override
    @Transactional(readOnly = true)
    public List<PointResponse> getPointsByStudentAndSemesterWithPermission(
            Long studentId,
            String semester,
            UserContext ctx) {

        if (!canAccessStudentPoints(studentId, ctx)) {
            throw new RuntimeException("Access denied");
        }

        return getPointsByStudentIdAndSemester(studentId, semester);
    }


    /* ================= UPDATE ================= */

    @Override
    public PointResponse updatePointWithPermission(
            Long id,
            UpdatePointRequest request,
            UserContext ctx) {

        if (!canAccessPoint(id, ctx)) {
            throw new RuntimeException("Access denied");
        }

        return updatePoint(id, request);
    }


    /* ================= DELETE ================= */

    @Override
    public void deletePointWithPermission(Long id, UserContext ctx) {

        if (!canAccessPoint(id, ctx)) {
            throw new RuntimeException("Access denied");
        }

        deletePoint(id);
    }

}
