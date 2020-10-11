package pl.mbalcer.enrollmentsystem.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.model.*;
import pl.mbalcer.enrollmentsystem.model.enumeration.CourseType;
import pl.mbalcer.enrollmentsystem.model.enumeration.StudyMode;
import pl.mbalcer.enrollmentsystem.model.enumeration.StudyType;
import pl.mbalcer.enrollmentsystem.repository.*;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class InitService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final FieldOfStudyRepository fieldOfStudyRepository;
    private final SubjectRepository subjectRepository;
    private final SubjectGroupRepository subjectGroupRepository;

    public InitService(UserRepository userRepository, PasswordEncoder passwordEncoder, StudentRepository studentRepository, FacultyRepository facultyRepository, FieldOfStudyRepository fieldOfStudyRepository, SubjectRepository subjectRepository, SubjectGroupRepository subjectGroupRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.fieldOfStudyRepository = fieldOfStudyRepository;
        this.subjectRepository = subjectRepository;
        this.subjectGroupRepository = subjectGroupRepository;
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

        Subject subject = new Subject(0l, "Graduation seminar", "description", Duration.ofHours(30), CourseType.SEMINAR, 4);
        subject = subjectRepository.save(subject);

        LocalDateTime startGroup = LocalDateTime.of(2020, 10, 12, 8, 0);
        SubjectGroup group1 = new SubjectGroup(0l, startGroup, LocalTime.of(1, 30), "UTP", 14, "prof Jan Kowalski", subject);
        subjectGroupRepository.save(group1);
    }

}
