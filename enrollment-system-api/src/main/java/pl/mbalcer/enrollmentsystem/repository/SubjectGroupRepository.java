package pl.mbalcer.enrollmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbalcer.enrollmentsystem.model.SubjectGroup;
import pl.mbalcer.enrollmentsystem.model.enumeration.GroupType;

import java.util.List;

@Repository
public interface SubjectGroupRepository extends JpaRepository<SubjectGroup, Long> {
    List<SubjectGroup> findAllByTeacherUsername(String teacherUsername);

    List<SubjectGroup> findAllByType(GroupType type);
}
