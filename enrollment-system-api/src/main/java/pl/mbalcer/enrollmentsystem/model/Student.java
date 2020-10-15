package pl.mbalcer.enrollmentsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.mbalcer.enrollmentsystem.model.enumeration.Role;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "students")
@PrimaryKeyJoinColumn(name = "userId")
public class Student extends User {
    private Long indexNumber;
    private Integer semester;

    @ManyToOne
    private FieldOfStudy fieldOfStudy;

    @ToString.Exclude
    @ManyToMany(mappedBy = "students")
    private List<SubjectGroup> groups = new ArrayList<>();

    public Student(User user, FieldOfStudy fieldOfStudy, Long indexNumber, Integer semester) {
        super(user.getId(), user.getUsername(), user.getPassword(), user.getFullName(), user.getEmail(), Role.STUDENT, false);
        this.fieldOfStudy = fieldOfStudy;
        this.indexNumber = indexNumber;
        this.semester = semester;
    }
}
