package pl.mbalcer.enrollmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.mbalcer.enrollmentsystem.model.Teacher;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUsername(String username);
    Optional<Teacher> findByFullName(String fullName);

    @Modifying
    @Query(value = "insert into teachers(user_id, faculty_id, consultations, room) VALUES(:user, null, :consultation, :room)",
        nativeQuery = true)
    void initializeTeacherTable(@Param("user") Long user,
                                @Param("consultation") String consultation,
                                @Param("room") String room);

    @Modifying
    @Query(value = "delete from teachers t where t.user_id=:user", nativeQuery = true)
    void deleteRecordTeacher(@Param("user") Long user);
}
