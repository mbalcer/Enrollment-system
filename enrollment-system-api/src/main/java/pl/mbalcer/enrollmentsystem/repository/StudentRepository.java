package pl.mbalcer.enrollmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.mbalcer.enrollmentsystem.model.Student;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUsername(String username);

    @Modifying
    @Query(value = "insert into students(user_id, field_of_study_id, index_number, semester) VALUES(:user, null, :index, :semester)",
            nativeQuery = true)
    void initializeStudentTable(@Param("user") Long user,
                                @Param("index") Long index,
                                @Param("semester") Integer semester);

    @Modifying
    @Query(value = "delete from students s where s.user_id=:user", nativeQuery = true)
    void deleteRecordStudent(@Param("user") Long user);
}
