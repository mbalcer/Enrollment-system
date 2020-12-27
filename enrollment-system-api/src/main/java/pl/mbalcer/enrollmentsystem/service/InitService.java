package pl.mbalcer.enrollmentsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class InitService {
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String hibernateType;

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
    private final RoleRepository roleRepository;

    public InitService(UserRepository userRepository, UserService userService, PasswordEncoder passwordEncoder, StudentRepository studentRepository, FacultyRepository facultyRepository, FieldOfStudyRepository fieldOfStudyRepository, SubjectRepository subjectRepository, SubjectGroupRepository subjectGroupRepository, TeacherRepository teacherRepository, AppointmentRepository appointmentRepository, RoleRepository roleRepository) {
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
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void isInit() {
        if (hibernateType.equals("create") || hibernateType.equals("create-drop")) {
            log.info("The data was initialized");
            init();
        } else {
            log.info("The data wasn't initialized");
        }
    }

    private void init() {
        Role student = new Role(0l, ERole.STUDENT);
        Role teacher = new Role(0l, ERole.TEACHER);
        Role admin = new Role(0l, ERole.ADMIN);

        student = roleRepository.save(student);
        teacher = roleRepository.save(teacher);
        admin = roleRepository.save(admin);

        Set<Role> studentRole = new HashSet<>();
        studentRole.add(student);
        Set<Role> teacherRole = new HashSet<>();
        teacherRole.add(teacher);
        Set<Role> adminRole = new HashSet<>();
        adminRole.add(admin);

        Faculty mathAndItFaculty = new Faculty(0l, "Faculty mathematics and IT", "Bydgoszcz", "MaI", null, null);
        mathAndItFaculty = facultyRepository.save(mathAndItFaculty);
        FieldOfStudy it = new FieldOfStudy(0l, "IT", StudyType.FIRST_CYCLE, StudyMode.FULL_TIME, mathAndItFaculty, null);
        it = fieldOfStudyRepository.save(it);

        User adminUser = new User(0l, "admin", passwordEncoder.encode("admin"), "admin@utp.edu.pl", "Admin", adminRole, true);
        userRepository.save(adminUser);

        Student studentUser = new Student(0L, "adamek", passwordEncoder.encode("adam123"), "Adam Kowalski", "adamrewrwer@utp.edu.pl", studentRole, 111000L, 7, it);
        studentUser = studentRepository.save(studentUser);
        userService.enableUser(studentUser.getUsername());

        Subject subject = new Subject(0l, "Graduation seminar", "description", Duration.ofHours(30), CourseType.SEMINAR, 4, "English");
        subject = subjectRepository.save(subject);

        Teacher teacherUser = new Teacher(mathAndItFaculty, "janek", passwordEncoder.encode("janek"), "Jan Kowalski", "jankow111@wp.pl", teacherRole);
        teacherUser = teacherRepository.save(teacherUser);
        userService.enableUser(teacherUser.getUsername());

        SubjectGroup group1 = new SubjectGroup(0l, LocalTime.of(1, 30), "UTP", 14, GroupType.ACCEPTED, subject, teacherUser, null, Arrays.asList(studentUser), Arrays.asList(it));
        group1 = subjectGroupRepository.save(group1);

        for (int i=1; i<10; i++) {
            Student newStudent = new Student(0L, "adamek"+i, passwordEncoder.encode("adam123"), "Adam "+ i +" Nowak", "adam"+i+"@utp.edu.pl", studentRole, 111000L + i, 7, it);
            newStudent = studentRepository.save(newStudent);

            group1.addStudent(newStudent);
            subjectGroupRepository.save(group1);

            Subject s = new Subject(0l, "Lecture "+i,
                    "Lorem ipsum dolor sit amet, consectetur adipisicing elit. A architecto, consequatur delectus dicta dignissimos dolores illum in ipsa ipsum laboriosam magni officia qui recusandae rerum soluta tenetur veniam vitae? Modi. \n\n" +
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. A architecto, consequatur delectus dicta dignissimos dolores illum in ipsa ipsum laboriosam magni officia qui recusandae rerum soluta tenetur veniam vitae? Modi.\n\n" +
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. A architecto, consequatur delectus dicta dignissimos dolores illum in ipsa ipsum laboriosam magni officia qui recusandae rerum soluta tenetur veniam vitae? Modi." +
                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. A architecto, consequatur delectus dicta dignissimos dolores illum in ipsa ipsum laboriosam magni officia qui recusandae rerum soluta tenetur veniam vitae? Modi.",
                    Duration.ofHours(i*5), CourseType.LECTURE, i, "English");
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
