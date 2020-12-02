package pl.mbalcer.enrollmentsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbalcer.enrollmentsystem.model.Role;
import pl.mbalcer.enrollmentsystem.model.User;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String fullName;
    private String email;
    private Set<Role> role;
    private Boolean isActive;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.role = user.getRoles();
        this.isActive = user.getIsActive();
    }
}
