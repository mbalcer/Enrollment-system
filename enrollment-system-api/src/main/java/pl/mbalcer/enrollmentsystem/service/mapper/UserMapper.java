package pl.mbalcer.enrollmentsystem.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mbalcer.enrollmentsystem.model.User;
import pl.mbalcer.enrollmentsystem.model.dto.UserDTO;
import pl.mbalcer.enrollmentsystem.repository.UserRepository;

import java.util.Optional;

@Component
public class UserMapper {
    private final UserRepository userRepository;

    @Autowired
    public UserMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User toUserEntity(UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findUserByUsername(userDTO.getUsername());
        if(optionalUser.isPresent())
            return optionalUser.get();
        else {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setFullName(userDTO.getFullName());
            return user;
        }
    }

    public UserDTO toUserDTO(User user) {
        return new UserDTO(user);
    }
}
