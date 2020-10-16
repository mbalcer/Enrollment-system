package pl.mbalcer.enrollmentsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultyDTO {
    private Long id;
    private String name;
    private String address;
}
