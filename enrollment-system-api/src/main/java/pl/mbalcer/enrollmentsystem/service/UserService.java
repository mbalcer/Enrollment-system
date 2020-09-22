package pl.mbalcer.enrollmentsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.errors.EmailAlreadyUsedException;
import pl.mbalcer.enrollmentsystem.errors.LoginAlreadyUsedException;
import pl.mbalcer.enrollmentsystem.model.User;
import pl.mbalcer.enrollmentsystem.model.dto.RegisterUserDTO;
import pl.mbalcer.enrollmentsystem.repository.UserRepository;
import pl.mbalcer.enrollmentsystem.service.mapper.UserMapper;

import javax.transaction.Transactional;

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

        User user = userMapper.toUserEntity(userDTO);
        String passwordEncrypted = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(passwordEncrypted);
        User saveUser = userRepository.save(user);
        return new ResponseEntity<>(userMapper.toUserDTO(saveUser), HttpStatus.CREATED);
    }
}
