package com.example.point_service.service;

import com.example.point_service.dto.request.CreatePointRequest;
import com.example.point_service.dto.request.UpdatePointRequest;
import com.example.point_service.dto.response.PointResponse;
import com.example.point_service.security.UserContext;

import java.util.List;

public interface PointService {



    PointResponse createPoint(CreatePointRequest request);

    PointResponse getPointById(Long id);

    List<PointResponse> getPointsByStudentId(Long studentId);

    List<PointResponse> getPointsByStudentIdAndSemester(Long studentId, String semester);

    List<PointResponse> getAllPoints(UserContext ctx);

    PointResponse updatePoint(Long id, UpdatePointRequest request);

    void deletePoint(Long id);

    void deletePointsByStudentId(Long studentId);




    PointResponse getPointByIdWithPermission(Long id, UserContext ctx);

    List<PointResponse> getMyPoints(UserContext ctx);

    List<PointResponse> getPointsByStudentWithPermission(Long studentId, UserContext ctx);

    List<PointResponse> getPointsByStudentAndSemesterWithPermission(
            Long studentId,
            String semester,
            UserContext ctx
    );

    PointResponse updatePointWithPermission(
            Long id,
            UpdatePointRequest request,
            UserContext ctx
    );

    void deletePointWithPermission(Long id, UserContext ctx);



    boolean canAccessPoint(Long pointId, UserContext ctx);

    boolean canAccessStudentPoints(Long studentId, UserContext ctx);
}