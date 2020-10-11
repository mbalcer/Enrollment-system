package pl.mbalcer.enrollmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mbalcer.enrollmentsystem.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
