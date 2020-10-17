package pl.mbalcer.enrollmentsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbalcer.enrollmentsystem.model.User;
import pl.mbalcer.enrollmentsystem.model.enumeration.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String fullName;
    private String email;
    private Role role;
    private boolean isActive;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.isActive = user.getIsActive();
    }
}
