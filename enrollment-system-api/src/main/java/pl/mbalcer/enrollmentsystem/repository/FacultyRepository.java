package pl.mbalcer.enrollmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbalcer.enrollmentsystem.model.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
