package com.school.student_service.repository;

import com.school.student_service.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, String> {
    Optional<School> findByCodeAndDeletedTrue(String schoolCode);
    Optional<School> findByCodeAndDeletedFalse(String schoolCode);
    Optional<School> findByIdAndDeletedFalse(String schoolId);
    boolean existsByCodeAndDeletedFalse(String schoolCode);
}
