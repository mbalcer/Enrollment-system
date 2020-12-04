package pl.mbalcer.enrollmentsystem.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbalcer.enrollmentsystem.model.enumeration.ERole;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String jwt;
    private String username;
    private String email;
    private List<ERole> role;
}
