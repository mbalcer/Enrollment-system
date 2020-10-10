package pl.mbalcer.enrollmentsystem.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.model.Student;
import pl.mbalcer.enrollmentsystem.model.User;
import pl.mbalcer.enrollmentsystem.repository.StudentRepository;
import pl.mbalcer.enrollmentsystem.repository.UserRepository;

import javax.annotation.PostConstruct;

@Service
public class InitService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;

    public InitService(UserRepository userRepository, PasswordEncoder passwordEncoder, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
    }

    @PostConstruct
    public void init() {
        User user = new User(0L, "admin", passwordEncoder.encode("admin"), "Admin", "admin@utp.edu.pl");
        Student student = new Student(user,"IT", 111000L, 7);
        studentRepository.save(student);
    }

}
