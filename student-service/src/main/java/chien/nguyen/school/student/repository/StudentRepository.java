package chien.nguyen.school.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import chien.nguyen.school.student.model.Student;
import java.util.*;

public interface StudentRepository extends JpaRepository<Student, Long> {
  List<Student> findBySchool_Id(Long schoolId);

}
