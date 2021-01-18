package pl.mbalcer.enrollmentsystem.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.mbalcer.enrollmentsystem.EnrollmentSystemApplication;
import pl.mbalcer.enrollmentsystem.errors.NotFoundException;
import pl.mbalcer.enrollmentsystem.model.Faculty;
import pl.mbalcer.enrollmentsystem.model.FieldOfStudy;
import pl.mbalcer.enrollmentsystem.model.dto.FieldOfStudyDTO;
import pl.mbalcer.enrollmentsystem.model.enumeration.StudyMode;
import pl.mbalcer.enrollmentsystem.model.enumeration.StudyType;
import pl.mbalcer.enrollmentsystem.repository.FacultyRepository;
import pl.mbalcer.enrollmentsystem.repository.FieldOfStudyRepository;
import pl.mbalcer.enrollmentsystem.service.mapper.FieldOfStudyMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EnrollmentSystemApplication.class)
public class FieldOfStudyServiceTest {
    private static final String DEFAULT_NAME = "IT";
    private static final StudyMode DEFAULT_STUDY_MODE = StudyMode.FULL_TIME;
    private static final StudyType DEFAULT_STUDY_TYPE = StudyType.FIRST_CYCLE;

    private static final String UPDATE_NAME = "NAME";
    private static final StudyMode UPDATE_MODE = StudyMode.PART_TIME;
    private static final StudyType UPDATE_TYPE = StudyType.SECOND_CYCLE;

    private Faculty DEFAULT_FACULTY = new Faculty(0l, "AAA", "BBB", "CCC", LocalDateTime.of(2020, 1, 1, 1, 0), null);

    @Autowired
    private FieldOfStudyService fieldOfStudyService;

    @Autowired
    private FieldOfStudyRepository fieldOfStudyRepository;

    @Autowired
    private FieldOfStudyMapper fieldOfStudyMapper;

    @Autowired
    private FacultyRepository facultyRepository;

    @BeforeEach
    public void setup() {
        fieldOfStudyRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    public void findAllTest() {
        FieldOfStudyDTO f1 = saveFieldOfStudy(getFieldOfStudyObjectWithData());
        FieldOfStudyDTO f2 = saveFieldOfStudy(getFieldOfStudyObjectWithData());

        List<FieldOfStudyDTO> expected = new ArrayList<>(Arrays.asList(f1, f2));
        List<FieldOfStudyDTO> actual = fieldOfStudyService.findAll();

        assertIterableEquals(expected, actual);
    }

    @Test
    public void findOneTest() {
        FieldOfStudyDTO saveFieldOfStudy = saveFieldOfStudy(getFieldOfStudyObjectWithData());
        assertNotNull(saveFieldOfStudy.getId());

        assertNotNull(fieldOfStudyService.findOne(saveFieldOfStudy.getId()));
    }


    @Test
    public void createTest() {
        FieldOfStudyDTO saveFieldOfStudy = saveFieldOfStudy(getFieldOfStudyObjectWithData());
        assertNotNull(saveFieldOfStudy.getId());

        FieldOfStudy getFieldOfStudy = fieldOfStudyRepository.findById(saveFieldOfStudy.getId()).orElseThrow(NotFoundException::new);
        assertEquals(DEFAULT_NAME, getFieldOfStudy.getName());
        assertEquals(DEFAULT_STUDY_MODE, getFieldOfStudy.getMode());
        assertEquals(DEFAULT_STUDY_TYPE, getFieldOfStudy.getType());
        assertEquals(DEFAULT_FACULTY.getId(), getFieldOfStudy.getFaculty().getId());
    }


    @Test
    public void updateTest() {
        FieldOfStudyDTO saveFieldOfStudy = saveFieldOfStudy(getFieldOfStudyObjectWithData());

        saveFieldOfStudy.setName(UPDATE_NAME);
        saveFieldOfStudy.setMode(UPDATE_MODE);
        saveFieldOfStudy.setType(UPDATE_TYPE);

        FieldOfStudyDTO update = fieldOfStudyService.update(saveFieldOfStudy, saveFieldOfStudy.getId());

        FieldOfStudy getFieldOfStudy = fieldOfStudyRepository.findById(update.getId()).orElseThrow(NotFoundException::new);
        assertEquals(saveFieldOfStudy, update);
        assertEquals(UPDATE_NAME, getFieldOfStudy.getName());
        assertEquals(UPDATE_MODE, getFieldOfStudy.getMode());
        assertEquals(UPDATE_TYPE, getFieldOfStudy.getType());
    }

    @Test
    public void deleteTest() {
        FieldOfStudyDTO saveFieldOfStudy = saveFieldOfStudy(getFieldOfStudyObjectWithData());
        fieldOfStudyService.delete(saveFieldOfStudy.getId());
        assertThrows(NotFoundException.class, () -> fieldOfStudyRepository.findById(saveFieldOfStudy.getId()).orElseThrow(NotFoundException::new));
    }


    private FieldOfStudy getFieldOfStudyObjectWithData() {
        FieldOfStudy fieldOfStudy = new FieldOfStudy();
        fieldOfStudy.setName(DEFAULT_NAME);
        fieldOfStudy.setMode(DEFAULT_STUDY_MODE);
        fieldOfStudy.setType(DEFAULT_STUDY_TYPE);
        DEFAULT_FACULTY = facultyRepository.save(DEFAULT_FACULTY);
        fieldOfStudy.setFaculty(DEFAULT_FACULTY);
        return fieldOfStudy;
    }

    private FieldOfStudyDTO saveFieldOfStudy(FieldOfStudy fieldOfStudy) {
        FieldOfStudyDTO fieldOfStudyDTO = fieldOfStudyMapper.toDto(fieldOfStudy);
        return fieldOfStudyService.create(fieldOfStudyDTO);
    }
}
