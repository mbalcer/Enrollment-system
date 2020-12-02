package pl.mbalcer.enrollmentsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public Student(Long id, String username, String password, String fullName, String email, Set<Role> roles, Long indexNumber, Integer semester, FieldOfStudy fieldOfStudy) {
        super(id, username, password, email, fullName, roles, true);
        this.indexNumber = indexNumber;
        this.semester = semester;
        this.fieldOfStudy = fieldOfStudy;
    }
}
