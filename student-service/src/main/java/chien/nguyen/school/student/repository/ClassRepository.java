package chien.nguyen.school.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import chien.nguyen.school.student.model.ClassRoom;

import java.util.List;

public interface ClassRepository extends JpaRepository<ClassRoom, Long> {

    List<ClassRoom> findBySchool_Id(Long schoolId);

}

