package pl.mbalcer.enrollmentsystem.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.model.Faculty;
import pl.mbalcer.enrollmentsystem.model.FieldOfStudy;
import pl.mbalcer.enrollmentsystem.model.Student;
import pl.mbalcer.enrollmentsystem.model.User;
import pl.mbalcer.enrollmentsystem.model.enumeration.StudyMode;
import pl.mbalcer.enrollmentsystem.model.enumeration.StudyType;
import pl.mbalcer.enrollmentsystem.repository.FacultyRepository;
import pl.mbalcer.enrollmentsystem.repository.FieldOfStudyRepository;
import pl.mbalcer.enrollmentsystem.repository.StudentRepository;
import pl.mbalcer.enrollmentsystem.repository.UserRepository;

import javax.annotation.PostConstruct;

@Service
public class InitService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final FieldOfStudyRepository fieldOfStudyRepository;

    public InitService(UserRepository userRepository, PasswordEncoder passwordEncoder, StudentRepository studentRepository, FacultyRepository facultyRepository, FieldOfStudyRepository fieldOfStudyRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.fieldOfStudyRepository = fieldOfStudyRepository;
    }

    @PostConstruct
    public void init() {
        User user = new User(0L, "admin", passwordEncoder.encode("admin"), "Admin", "admin@utp.edu.pl");
        Student student = new Student(user,"IT", 111000L, 7);
        studentRepository.save(student);

        Faculty mathAndItFaculty = new Faculty(0l, "Faculty mathematics and IT", "Bydgoszcz", null);
        mathAndItFaculty = facultyRepository.save(mathAndItFaculty);
        FieldOfStudy it = new FieldOfStudy(0l, "IT", StudyType.FIRST_CYCLE, StudyMode.FULL_TIME, mathAndItFaculty);
        fieldOfStudyRepository.save(it);
    }

}
