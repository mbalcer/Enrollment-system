package pl.mbalcer.enrollmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbalcer.enrollmentsystem.model.Teacher;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUsername(String username);
    Optional<Teacher> findByFullName(String fullName);
}
