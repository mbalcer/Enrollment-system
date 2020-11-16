package pl.mbalcer.enrollmentsystem.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String jwt;
    private String username;
    private String email;
    private String role;
}
