package pl.mbalcer.enrollmentsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "teachers")
@PrimaryKeyJoinColumn(name = "userId")
public class Teacher extends User  {

    @ManyToOne
    private Faculty faculty;

    @OneToMany(mappedBy = "teacher")
    private List<SubjectGroup> subjectGroups;

    public Teacher(User user, Faculty faculty) {
        super(user.getId(), user.getUsername(), user.getPassword(), user.getFullName(), user.getEmail());
        this.faculty = faculty;
    }

    public Teacher(Faculty faculty, String username, String password, String fullName, String email) {
        super(0l, username, password, fullName, email);
        this.faculty = faculty;
    }
}
