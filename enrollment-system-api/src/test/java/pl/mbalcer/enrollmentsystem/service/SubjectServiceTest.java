package pl.mbalcer.enrollmentsystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.mbalcer.enrollmentsystem.EnrollmentSystemApplication;
import pl.mbalcer.enrollmentsystem.errors.NotFoundException;
import pl.mbalcer.enrollmentsystem.model.Subject;
import pl.mbalcer.enrollmentsystem.model.dto.SubjectDTO;
import pl.mbalcer.enrollmentsystem.model.enumeration.CourseType;
import pl.mbalcer.enrollmentsystem.repository.SubjectRepository;
import pl.mbalcer.enrollmentsystem.service.mapper.SubjectMapper;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EnrollmentSystemApplication.class)
public class SubjectServiceTest {
    private static final String DEFAULT_NAME = "Programming";
    private static final String DEFAULT_DESCRIPTION = "Programming in Java";
    private static final Integer DEFAULT_ECTS = 5;
    private static final Duration DEFAULT_NUMBER_OF_HOURS = Duration.ZERO;
    private static final CourseType DEFAULT_COURSE_TYPE = CourseType.LECTURE;
    private static final String DEFAULT_LANGUAGE = "English";
    private static final CourseType UPDATE_COURSE_TYPE = CourseType.LAB;
    private static final String UPDATE_LANGUAGE = "Polish";
    private static final int UPDATE_ECTS = 0;
    private static final String UPDATE_NAME = "Subject";

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SubjectMapper subjectMapper;

    @BeforeEach
    public void init() {
        subjectRepository.deleteAll();
    }

    @Test
    public void findAllTest() {
        SubjectDTO s1 = saveSubject();
        SubjectDTO s2 = saveSubject();

        List<SubjectDTO> expected = new ArrayList<>(Arrays.asList(s1, s2));
        List<SubjectDTO> actual = subjectService.findAll();

        assertIterableEquals(expected, actual);
    }


    @Test
    public void createFacultyTest() {
        SubjectDTO saveSubject = saveSubject();
        assertNotNull(saveSubject.getId());

        Subject getSubject = subjectRepository.findById(saveSubject.getId()).orElseThrow(NotFoundException::new);
        assertEquals(DEFAULT_NAME, getSubject.getName());
        assertEquals(DEFAULT_DESCRIPTION, getSubject.getDescription());
        assertEquals(DEFAULT_ECTS, getSubject.getECTS());
        assertEquals(DEFAULT_NUMBER_OF_HOURS, getSubject.getNumberOfHours());
        assertEquals(DEFAULT_COURSE_TYPE, getSubject.getCourseType());
        assertEquals(DEFAULT_LANGUAGE, getSubject.getLanguage());
    }

    @Test
    public void updateFacultyTest() {
        SubjectDTO saveSubject = saveSubject();

        saveSubject.setName(UPDATE_NAME);
        saveSubject.setECTS(UPDATE_ECTS);
        saveSubject.setLanguage(UPDATE_LANGUAGE);
        saveSubject.setCourseType(UPDATE_COURSE_TYPE);

        SubjectDTO update = subjectService.update(saveSubject, saveSubject.getId());

        SubjectDTO getSubject = subjectService.findOne(update.getId()).orElseThrow(NotFoundException::new);
        assertEquals(saveSubject, update);
        assertEquals(UPDATE_NAME, getSubject.getName());
        assertEquals(UPDATE_ECTS, getSubject.getECTS());
        assertEquals(UPDATE_LANGUAGE, getSubject.getLanguage());
        assertEquals(UPDATE_COURSE_TYPE, getSubject.getCourseType());
    }

    @Test
    public void deleteFacultyTest() {
        SubjectDTO saveSubject = saveSubject();
        assertNotNull(saveSubject.getId());
        subjectService.delete(saveSubject.getId());
        assertThrows(NotFoundException.class, () -> subjectRepository.findById(saveSubject.getId()).orElseThrow(NotFoundException::new));
    }

    private SubjectDTO saveSubject() {
        Subject subject = new Subject();
        subject.setName(DEFAULT_NAME);
        subject.setDescription(DEFAULT_DESCRIPTION);
        subject.setECTS(DEFAULT_ECTS);
        subject.setNumberOfHours(DEFAULT_NUMBER_OF_HOURS);
        subject.setCourseType(DEFAULT_COURSE_TYPE);
        subject.setLanguage(DEFAULT_LANGUAGE);
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);
        return subjectService.create(subjectDTO);
    }
}
