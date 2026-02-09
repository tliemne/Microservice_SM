package com.example.point_service.repository;

import com.example.point_service.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findByStudentId(Long studentId);
    List<Point> findByStudentIdAndSemester(Long studentId, String semester);
    Optional<Point> findByStudentIdAndSubjectIdAndSemester(Long studentId, Long subjectId, String semester);

}
