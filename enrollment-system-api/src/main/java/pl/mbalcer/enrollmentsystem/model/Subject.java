package pl.mbalcer.enrollmentsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbalcer.enrollmentsystem.model.enumeration.CourseType;

import javax.persistence.*;
import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 8000)
    private String description;
    private Duration numberOfHours;
    private CourseType courseType;
    private Integer ECTS;
    private String language;
}
