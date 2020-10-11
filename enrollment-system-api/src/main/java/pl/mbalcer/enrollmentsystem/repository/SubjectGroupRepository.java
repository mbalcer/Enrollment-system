package pl.mbalcer.enrollmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbalcer.enrollmentsystem.model.SubjectGroup;

@Repository
public interface SubjectGroupRepository extends JpaRepository<SubjectGroup, Long> {
}
