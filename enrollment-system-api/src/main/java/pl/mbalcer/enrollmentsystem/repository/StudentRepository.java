package pl.mbalcer.enrollmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbalcer.enrollmentsystem.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
