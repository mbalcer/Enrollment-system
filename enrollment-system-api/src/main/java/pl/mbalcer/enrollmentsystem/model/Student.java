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

    public Student(User user, Long indexNumber, Integer semester, FieldOfStudy fieldOfStudy, List<SubjectGroup> groups) {
        super(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getFullName(), user.getRoles(), user.getIsActive());
        this.indexNumber = indexNumber;
        this.semester = semester;
        this.fieldOfStudy = fieldOfStudy;
        this.groups = groups;
    }

    public Student(User user) {
        this(user, 0l, 1, null, null);
    }

    public Student(Long id, String username, String password, String fullName, String email, Set<Role> roles, Long indexNumber, Integer semester, FieldOfStudy fieldOfStudy) {
        super(id, username, password, email, fullName, roles, true);
        this.indexNumber = indexNumber;
        this.semester = semester;
        this.fieldOfStudy = fieldOfStudy;
    }
}
