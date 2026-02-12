package chien.nguyen.school.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import chien.nguyen.school.student.model.School;



public interface SchoolRepository extends JpaRepository<School, Long> {

}
