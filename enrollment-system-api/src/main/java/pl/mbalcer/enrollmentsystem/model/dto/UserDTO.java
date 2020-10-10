package pl.mbalcer.enrollmentsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbalcer.enrollmentsystem.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String fullName;
    private String email;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
    }
}
