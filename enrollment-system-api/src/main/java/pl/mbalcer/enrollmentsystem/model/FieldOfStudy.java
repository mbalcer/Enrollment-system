package pl.mbalcer.enrollmentsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.mbalcer.enrollmentsystem.model.enumeration.StudyMode;
import pl.mbalcer.enrollmentsystem.model.enumeration.StudyType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "fields_of_study")
public class FieldOfStudy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private StudyType type;
    private StudyMode mode;

    @ManyToOne
    private Faculty faculty;

    @ToString.Exclude
    @ManyToMany(mappedBy = "fieldsOfStudy")
    private List<SubjectGroup> groups = new ArrayList<>();
}
