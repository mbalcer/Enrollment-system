package pl.mbalcer.enrollmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbalcer.enrollmentsystem.model.FieldOfStudy;

@Repository
public interface FieldOfStudyRepository extends JpaRepository<FieldOfStudy, Long> {
}
