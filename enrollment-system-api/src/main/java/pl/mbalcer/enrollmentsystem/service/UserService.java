package pl.mbalcer.enrollmentsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.errors.EmailAlreadyUsedException;
import pl.mbalcer.enrollmentsystem.errors.LoginAlreadyUsedException;
import pl.mbalcer.enrollmentsystem.errors.UserNotFoundException;
import pl.mbalcer.enrollmentsystem.model.User;
import pl.mbalcer.enrollmentsystem.model.dto.RegisterUserDTO;
import pl.mbalcer.enrollmentsystem.model.dto.UserDTO;
import pl.mbalcer.enrollmentsystem.repository.UserRepository;
import pl.mbalcer.enrollmentsystem.service.mapper.UserMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public ResponseEntity registerUser(RegisterUserDTO userDTO) {
        if (userRepository.findUserByUsername(userDTO.getUsername()).isPresent())
            throw new LoginAlreadyUsedException();
        else if (userRepository.findOneByEmail(userDTO.getEmail()).isPresent())
            throw new EmailAlreadyUsedException();

        User user = userMapper.toEntity(userDTO);
        String passwordEncrypted = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(passwordEncrypted);
        User saveUser = userRepository.save(user);
        return new ResponseEntity<>(userMapper.toDto(saveUser), HttpStatus.CREATED);
    }

    public ResponseEntity getUser(String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isEmpty())
            throw new UserNotFoundException();

        return new ResponseEntity(userMapper.toDto(userOptional.get()), HttpStatus.OK);
    }

    public List<UserDTO> findAll() {
        List<User> all = userRepository.findAll();
        return userMapper.toDto(all);
    }

    public UserDTO changeRole(UserDTO userDTO) {
        Optional<User> userByUsername = userRepository.findUserByUsername(userDTO.getUsername());
        if(userByUsername.isEmpty())
            throw new UserNotFoundException();

        User user = userByUsername.get();
        user.setRole(userDTO.getRole());
        return userMapper.toDto(userRepository.save(user));
    }
}
