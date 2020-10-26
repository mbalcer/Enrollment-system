package pl.mbalcer.enrollmentsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectGroupDTO {
    private Long id;
    private LocalTime courseTime;
    private String place;
    private Integer numberOfPlaces;
    private String nameTeacher;

    private List<AppointmentDTO> timeTableDTO;
    private SubjectDTO subjectDTO;
    private List<FieldOfStudyDTO> fieldsOfStudyDTO;
    private List<StudentDTO> studentsDTO;
}
