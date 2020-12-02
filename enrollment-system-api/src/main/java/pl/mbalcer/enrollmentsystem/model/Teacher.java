package pl.mbalcer.enrollmentsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "teachers")
@PrimaryKeyJoinColumn(name = "userId")
public class Teacher extends User  {
    private String room = "-";
    private String consultations = "-";

    @ManyToOne
    private Faculty faculty;

    @OneToMany(mappedBy = "teacher")
    private List<SubjectGroup> subjectGroups;

    public Teacher(Faculty faculty, String username, String password, String fullName, String email, Set<Role> roles) {
        super(0l, username, password, email, fullName, roles, false);
        this.faculty = faculty;
    }
}
