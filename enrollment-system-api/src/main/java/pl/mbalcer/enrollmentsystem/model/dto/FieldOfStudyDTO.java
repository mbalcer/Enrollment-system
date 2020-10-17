package pl.mbalcer.enrollmentsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbalcer.enrollmentsystem.model.enumeration.StudyMode;
import pl.mbalcer.enrollmentsystem.model.enumeration.StudyType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldOfStudyDTO {
    private Long id;
    private String name;
    private StudyType type;
    private StudyMode mode;
    private String abbreviationFaculty;
}
