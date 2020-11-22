package pl.mbalcer.enrollmentsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.model.*;
import pl.mbalcer.enrollmentsystem.model.enumeration.*;
import pl.mbalcer.enrollmentsystem.repository.*;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

@Service
public class InitService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final FieldOfStudyRepository fieldOfStudyRepository;
    private final SubjectRepository subjectRepository;
    private final SubjectGroupRepository subjectGroupRepository;
    private final TeacherRepository teacherRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public InitService(UserRepository userRepository, UserService userService, PasswordEncoder passwordEncoder, StudentRepository studentRepository, FacultyRepository facultyRepository, FieldOfStudyRepository fieldOfStudyRepository, SubjectRepository subjectRepository, SubjectGroupRepository subjectGroupRepository, TeacherRepository teacherRepository, AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.fieldOfStudyRepository = fieldOfStudyRepository;
        this.subjectRepository = subjectRepository;
        this.subjectGroupRepository = subjectGroupRepository;
        this.teacherRepository = teacherRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @PostConstruct
    public void init() {
        Faculty mathAndItFaculty = new Faculty(0l, "Faculty mathematics and IT", "Bydgoszcz", "MaI", null);
        mathAndItFaculty = facultyRepository.save(mathAndItFaculty);
        FieldOfStudy it = new FieldOfStudy(0l, "IT", StudyType.FIRST_CYCLE, StudyMode.FULL_TIME, mathAndItFaculty, null);
        it = fieldOfStudyRepository.save(it);

        User admin = new User(0l, "admin", passwordEncoder.encode("admin"), "admin@utp.edu.pl", "Admin", Role.ADMIN, true);
        userRepository.save(admin);

        Student student = new Student(0L, "adamek", passwordEncoder.encode("adam123"), "Adam Kowalski", "adamrewrwer@utp.edu.pl", 111000L, 7, it);
        student = studentRepository.save(student);
        userService.enableUser(student.getUsername());

        Subject subject = new Subject(0l, "Graduation seminar", "description", Duration.ofHours(30), CourseType.SEMINAR, 4);
        subject = subjectRepository.save(subject);

        Teacher teacher = new Teacher(mathAndItFaculty, "janek", passwordEncoder.encode("janek"), "Jan Kowalski", "jankow111@wp.pl");
        teacher = teacherRepository.save(teacher);
        userService.enableUser(teacher.getUsername());

        SubjectGroup group1 = new SubjectGroup(0l, LocalTime.of(1, 30), "UTP", 14, GroupType.ACCEPTED, subject, teacher, null, Arrays.asList(student), Arrays.asList(it));
        group1 = subjectGroupRepository.save(group1);

        for (int i=1; i<10; i++) {
            Student newStudent = new Student(0L, "adamek"+i, passwordEncoder.encode("adam123"), "Adam "+ i +" Nowak", "adam"+i+"@utp.edu.pl", 111000L + i, 7, it);
            newStudent = studentRepository.save(newStudent);

            group1.addStudent(newStudent);
            subjectGroupRepository.save(group1);

            Subject s = new Subject(0l, "Lecture "+i,
                    "Lorem ipsum dolor sit amet, consectetur adipisicing elit. A architecto, consequatur delectus dicta dignissimos dolores illum in ipsa ipsum laboriosam magni officia qui recusandae rerum soluta tenetur veniam vitae? Modi. \n\n" +
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. A architecto, consequatur delectus dicta dignissimos dolores illum in ipsa ipsum laboriosam magni officia qui recusandae rerum soluta tenetur veniam vitae? Modi.\n\n" +
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. A architecto, consequatur delectus dicta dignissimos dolores illum in ipsa ipsum laboriosam magni officia qui recusandae rerum soluta tenetur veniam vitae? Modi." +
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. A architecto, consequatur delectus dicta dignissimos dolores illum in ipsa ipsum laboriosam magni officia qui recusandae rerum soluta tenetur veniam vitae? Modi.",
                    Duration.ofHours(i*5), CourseType.LECTURE, i);
            subjectRepository.save(s);
        }

        LocalDateTime startTime = LocalDateTime.of(2020, 10, 12, 8, 0);
        LocalDateTime endTime = LocalDateTime.of(2020, 10, 12, 9, 30);

        for (int i=0; i<10; i++) {
            Appointment appointment = new Appointment(0l, startTime, endTime, group1);
            appointmentRepository.save(appointment);
            startTime = startTime.plusDays(7);
            endTime = endTime.plusDays(7);
        }
    }

}
