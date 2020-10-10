package pl.mbalcer.enrollmentsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "students")
@PrimaryKeyJoinColumn(name = "userId")
public class Student extends User {
    private String fieldOfStudy;
    private Long indexNumber;
    private Integer semester;

    public Student(User user, String fieldOfStudy, Long indexNumber, Integer semester) {
        super(user.getId(), user.getUsername(), user.getPassword(), user.getFullName(), user.getEmail());
        this.fieldOfStudy = fieldOfStudy;
        this.indexNumber = indexNumber;
        this.semester = semester;
    }
}
