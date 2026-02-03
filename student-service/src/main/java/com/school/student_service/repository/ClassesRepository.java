package com.school.student_service.repository;

import com.school.student_service.model.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, String> {
    Optional<Classes> findByCodeAndDeletedTrue(String code);
    Optional<Classes> findByCodeAndDeletedFalse(String code);
    Optional<Classes> findByIdAndDeletedFalse(String classId);

    boolean existsByCodeAndDeletedFalse(String code);
}
