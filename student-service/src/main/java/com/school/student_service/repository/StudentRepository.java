package com.school.student_service.repository;

import com.school.student_service.model.School;
import com.school.student_service.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByCodeAndDeletedFalse(String code);
    Optional<Student> findByCodeAndDeletedTrue(String code);

    boolean existsByCodeAndDeletedFalse(String code);
}
