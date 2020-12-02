package pl.mbalcer.enrollmentsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbalcer.enrollmentsystem.model.Role;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private String username;
    private String fullName;
    private String email;
    private Set<Role> role;
    private Boolean isActive;

    private Long indexNumber;
    private Integer semester;
    private FieldOfStudyDTO fieldOfStudyDTO;
}
