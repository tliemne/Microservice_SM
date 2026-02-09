package com.example.point_service.service.impl;

import com.example.point_service.dto.request.CreatePointRequest;
import com.example.point_service.dto.request.UpdatePointRequest;
import com.example.point_service.dto.response.PointResponse;
import com.example.point_service.entity.Point;
import com.example.point_service.entity.Subject;
import com.example.point_service.mapper.PointMapper;
import com.example.point_service.repository.PointRepository;
import com.example.point_service.repository.SubjectRepository;
import com.school.common_library.security.UserContext;
import com.example.point_service.service.PointService;

import com.school.common_library.exception.AppException;
import com.school.common_library.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final SubjectRepository subjectRepository;
    private final PointMapper pointMapper;

<<<<<<< HEAD
=======

>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51

    @Override
    public PointResponse createPoint(CreatePointRequest request) {

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() ->
                        new AppException(ErrorCode.CLASSES_NOT_EXISTED)
                );

        Point point = Point.builder()
                .studentId(request.getStudentId())
<<<<<<< HEAD
                .schoolId(request.getSchoolId()) // ✅ thêm schoolId
=======
                .schoolId(request.getSchoolId())
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
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
                        new AppException(ErrorCode.STUDENT_NOT_EXISTED)
                );

        return pointMapper.pointToPointResponse(point);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PointResponse> getPointsByStudentId(Long studentId) {

        return pointRepository.findByStudentId(studentId)
                .stream()
                .map(pointMapper::pointToPointResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PointResponse> getPointsByStudentIdAndSemester(Long studentId, String semester) {

        return pointRepository
                .findByStudentIdAndSemester(studentId, semester)
                .stream()
                .map(pointMapper::pointToPointResponse)
                .toList();
    }


<<<<<<< HEAD
=======

>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
    @Override
    @Transactional(readOnly = true)
    public List<PointResponse> getAllPoints(UserContext ctx) {

        log.info("Get all points - user={}, role={}, schoolId={}",
                ctx.getUsername(), ctx.getRoles(), ctx.getSchoolId());

        List<Point> points = pointRepository.findAll();
<<<<<<< HEAD
=======

>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
        if (ctx.isAdmin()) {
            return points.stream()
                    .map(pointMapper::pointToPointResponse)
                    .toList();
        }

        if (ctx.isSchoolManager()) {

            return points.stream()
                    .filter(p ->
                            p.getSchoolId() != null
                                    && p.getSchoolId().equals(ctx.getSchoolId())
                    )
                    .map(pointMapper::pointToPointResponse)
                    .toList();
        }

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
                        new AppException(ErrorCode.STUDENT_NOT_EXISTED)
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


<<<<<<< HEAD
=======

>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
    @Override
    public void deletePoint(Long id) {

        if (!pointRepository.existsById(id)) {
            throw new AppException(ErrorCode.STUDENT_NOT_EXISTED);
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


<<<<<<< HEAD
    @Override
    public boolean canAccessPoint(Long pointId, UserContext ctx) {


=======

    @Override
    public boolean canAccessPoint(Long pointId, UserContext ctx) {

>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
        if (ctx.isAdmin()) {
            return true;
        }

<<<<<<< HEAD

=======
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
        if (ctx.isSchoolManager()) {
            return false;
        }

        Point point = pointRepository.findById(pointId).orElse(null);

        if (point == null) {
            return false;
        }

<<<<<<< HEAD

=======
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
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

<<<<<<< HEAD

=======
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
        if (ctx.isStudent()) {

            return ctx.getStudentId() != null
                    && ctx.getStudentId().equals(studentId);
        }

        if (ctx.isSchoolManager()) {

            List<Point> points = pointRepository.findByStudentId(studentId);

            if (points.isEmpty()) {
                return false;
            }

            return points.stream()
                    .allMatch(p ->
                            p.getSchoolId() != null
                                    && p.getSchoolId().equals(ctx.getSchoolId())
                    );
        }

        return false;
    }



    @Override
    @Transactional(readOnly = true)
    public PointResponse getPointByIdWithPermission(Long id, UserContext ctx) {

        PointResponse response = getPointById(id);

<<<<<<< HEAD

=======
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
        if (ctx.isStudent()) {

            if (!ctx.getStudentId().equals(response.getStudentId())) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
        }

<<<<<<< HEAD

        if (ctx.isSchoolManager()) {

            Point point = pointRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Point not found"));
=======
        if (ctx.isSchoolManager()) {

            Point point = pointRepository.findById(id)
                    .orElseThrow(() ->
                            new AppException(ErrorCode.STUDENT_NOT_EXISTED)
                    );
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51

            if (point.getSchoolId() == null
                    || !point.getSchoolId().equals(ctx.getSchoolId())) {

<<<<<<< HEAD
                throw new RuntimeException("Access denied");
=======
                throw new AppException(ErrorCode.UNAUTHORIZED);
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
            }
        }

        return response;
    }

<<<<<<< HEAD


=======
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
    @Override
    @Transactional(readOnly = true)
    public List<PointResponse> getMyPoints(UserContext ctx) {

        if (!ctx.isStudent()) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        if (ctx.getStudentId() == null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return getPointsByStudentId(ctx.getStudentId());
    }

<<<<<<< HEAD


=======
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
    @Override
    @Transactional(readOnly = true)
    public List<PointResponse> getPointsByStudentWithPermission(
            Long studentId, UserContext ctx) {

        if (!canAccessStudentPoints(studentId, ctx)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        return getPointsByStudentId(studentId);
    }

<<<<<<< HEAD

=======
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
    @Override
    @Transactional(readOnly = true)
    public List<PointResponse> getPointsByStudentAndSemesterWithPermission(
            Long studentId,
            String semester,
            UserContext ctx) {

        if (!canAccessStudentPoints(studentId, ctx)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        return getPointsByStudentIdAndSemester(studentId, semester);
    }

<<<<<<< HEAD


=======
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
    @Override
    public PointResponse updatePointWithPermission(
            Long id,
            UpdatePointRequest request,
            UserContext ctx) {

        if (!canAccessPoint(id, ctx)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        return updatePoint(id, request);
    }

<<<<<<< HEAD


=======
>>>>>>> 75ddd60e06d84e8b139f6bda6a5ad24477274b51
    @Override
    public void deletePointWithPermission(Long id, UserContext ctx) {

        if (!canAccessPoint(id, ctx)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        deletePoint(id);
    }

}
