package pl.mbalcer.enrollmentsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.errors.UserNotFoundException;
import pl.mbalcer.enrollmentsystem.model.User;
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
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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

    public void enableUser(String username) {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if(optionalUser.isEmpty())
            throw new UserNotFoundException();

        User user = optionalUser.get();
        user.setIsActive(true);
    }
}
