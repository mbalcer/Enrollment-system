package pl.mbalcer.enrollmentsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "subject_groups")
public class SubjectGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startTime;
    private LocalTime courseTime;
    private String place;
    private Integer numberOfPlaces;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Teacher teacher;

    @ManyToMany
    @JoinTable(
            name = "students_in_groups",
            joinColumns = @JoinColumn(name = "subject_groups_id"),
            inverseJoinColumns = @JoinColumn(name = "students_id"))
    private List<Student> students = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "groups_in_fields_of_study",
            joinColumns = @JoinColumn(name = "subject_groups_id"),
            inverseJoinColumns = @JoinColumn(name = "fields_of_study_id"))
    private List<FieldOfStudy> fieldsOfStudy = new ArrayList<>();

    public void addStudent(Student student) {
        this.students.add(student);
        student.getGroups().add(this);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
        student.getGroups().remove(this);
    }

    public void addFieldOfStudy(FieldOfStudy fieldOfStudy) {
        this.fieldsOfStudy.add(fieldOfStudy);
        fieldOfStudy.getGroups().add(this);
    }

    public void removeFieldOfStudy(FieldOfStudy fieldOfStudy) {
        this.fieldsOfStudy.remove(fieldOfStudy);
        fieldOfStudy.getGroups().remove(this);
    }
}
