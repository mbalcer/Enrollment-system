package pl.mbalcer.enrollmentsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.model.User;
import pl.mbalcer.enrollmentsystem.repository.UserRepository;

import javax.annotation.PostConstruct;

@Service
public class InitService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        User user = new User(0L, "admin", passwordEncoder.encode("admin"), "Admin", "admin@utp.edu.pl", "IT");
        userRepository.save(user);
    }

}
