package pl.mbalcer.enrollmentsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbalcer.enrollmentsystem.model.enumeration.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    private String username;
    private String fullName;
    private String email;
    private Role role;
    private Boolean isActive;

    private String room = "-";
    private String consultations = "-";
    private FacultyDTO facultyDTO;
}
