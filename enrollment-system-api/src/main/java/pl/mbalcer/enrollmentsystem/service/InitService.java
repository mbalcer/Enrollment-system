package pl.mbalcer.enrollmentsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.model.*;
import pl.mbalcer.enrollmentsystem.model.enumeration.CourseType;
import pl.mbalcer.enrollmentsystem.model.enumeration.Role;
import pl.mbalcer.enrollmentsystem.model.enumeration.StudyMode;
import pl.mbalcer.enrollmentsystem.model.enumeration.StudyType;
import pl.mbalcer.enrollmentsystem.repository.*;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

@Service
public class InitService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final FieldOfStudyRepository fieldOfStudyRepository;
    private final SubjectRepository subjectRepository;
    private final SubjectGroupRepository subjectGroupRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public InitService(UserRepository userRepository, PasswordEncoder passwordEncoder, StudentRepository studentRepository, FacultyRepository facultyRepository, FieldOfStudyRepository fieldOfStudyRepository, SubjectRepository subjectRepository, SubjectGroupRepository subjectGroupRepository, TeacherRepository teacherRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.fieldOfStudyRepository = fieldOfStudyRepository;
        this.subjectRepository = subjectRepository;
        this.subjectGroupRepository = subjectGroupRepository;
        this.teacherRepository = teacherRepository;
    }

    @PostConstruct
    public void init() {
        Faculty mathAndItFaculty = new Faculty(0l, "Faculty mathematics and IT", "Bydgoszcz", null);
        mathAndItFaculty = facultyRepository.save(mathAndItFaculty);
        FieldOfStudy it = new FieldOfStudy(0l, "IT", StudyType.FIRST_CYCLE, StudyMode.FULL_TIME, mathAndItFaculty, null);
        it = fieldOfStudyRepository.save(it);

        User user = new User(0L, "admin", passwordEncoder.encode("admin"), "Admin", "admin@utp.edu.pl", Role.STUDENT, true);
        Student student = new Student(user, it, 111000L, 7);
        student = studentRepository.save(student);

        Subject subject = new Subject(0l, "Graduation seminar", "description", Duration.ofHours(30), CourseType.SEMINAR, 4);
        subject = subjectRepository.save(subject);

        Teacher teacher = new Teacher(mathAndItFaculty, "janek", passwordEncoder.encode("janek"), "Jan Kowalski", "jankow111@wp.pl");
        teacher = teacherRepository.save(teacher);

        LocalDateTime startGroup = LocalDateTime.of(2020, 10, 12, 8, 0);
        SubjectGroup group1 = new SubjectGroup(0l, startGroup, LocalTime.of(1, 30), "UTP", 14, subject, teacher, Arrays.asList(student), Arrays.asList(it));
        subjectGroupRepository.save(group1);
    }

}
