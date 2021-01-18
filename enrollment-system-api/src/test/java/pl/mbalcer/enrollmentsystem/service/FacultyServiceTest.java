package pl.mbalcer.enrollmentsystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.mbalcer.enrollmentsystem.EnrollmentSystemApplication;
import pl.mbalcer.enrollmentsystem.errors.BadRequestException;
import pl.mbalcer.enrollmentsystem.errors.NotFoundException;
import pl.mbalcer.enrollmentsystem.model.Faculty;
import pl.mbalcer.enrollmentsystem.model.dto.FacultyDTO;
import pl.mbalcer.enrollmentsystem.repository.FacultyRepository;
import pl.mbalcer.enrollmentsystem.service.mapper.FacultyMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EnrollmentSystemApplication.class)
public class FacultyServiceTest {
    private static final String DEFAULT_NAME = "Faculty mathematics and IT";
    private static final String DEFAULT_ADDRESS = "Warsaw";
    private static final String DEFAULT_ABBREVIATION = "FMaIT";
    private static final LocalDateTime DEFAULT_START_REGISTRATION = LocalDateTime.of(2021, 01, 01, 10, 0);
    private static final String UPDATE_FACULTY_NAME = "FACULTY";
    private static final String UPDATE_FACULTY_ABBREVIATION = "ABC";
    private static final LocalDateTime UPDATE_START_REGISTRATION = LocalDateTime.of(2019, 01, 12, 10, 00);

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private FacultyMapper facultyMapper;

    @BeforeEach
    public void init() {
        facultyRepository.deleteAll();
    }

    @Test
    public void findAllTest() {
        FacultyDTO f1 = saveFaculty(getFacultyObjectWithData());
        FacultyDTO f2 = saveFaculty(getFacultyObjectWithData());
        List<FacultyDTO> expected = new ArrayList<>(Arrays.asList(f1, f2));

        List<FacultyDTO> actual = facultyService.findAll();

        assertIterableEquals(expected, actual);
    }

    @Test
    public void findByAbbreviationTest() {
        FacultyDTO saveFaculty = saveFaculty(getFacultyObjectWithData());
        Faculty facultyByAbbreviation = facultyService.findOneByAbbreviation(saveFaculty.getAbbreviation()).orElseThrow(NotFoundException::new);

        assertEquals(DEFAULT_NAME, facultyByAbbreviation.getName());
        assertEquals(DEFAULT_ADDRESS, facultyByAbbreviation.getAddress());
        assertEquals(DEFAULT_ABBREVIATION, facultyByAbbreviation.getAbbreviation());
        assertEquals(DEFAULT_START_REGISTRATION, facultyByAbbreviation.getStartRegistration());
    }

    @Test
    public void createTest() {
        FacultyDTO saveFaculty = saveFaculty(getFacultyObjectWithData());
        assertNotNull(saveFaculty.getId());

        Faculty getFaculty = facultyRepository.findById(saveFaculty.getId()).orElseThrow(NotFoundException::new);
        assertEquals(DEFAULT_NAME, getFaculty.getName());
        assertEquals(DEFAULT_ADDRESS, getFaculty.getAddress());
        assertEquals(DEFAULT_ABBREVIATION, getFaculty.getAbbreviation());
        assertEquals(DEFAULT_START_REGISTRATION, getFaculty.getStartRegistration());
    }

    @Test
    public void updateTest() {
        FacultyDTO saveFaculty = saveFaculty(getFacultyObjectWithData());

        saveFaculty.setAbbreviation(UPDATE_FACULTY_ABBREVIATION);
        saveFaculty.setName(UPDATE_FACULTY_NAME);
        saveFaculty.setStartRegistration(UPDATE_START_REGISTRATION);

        FacultyDTO update = facultyService.update(saveFaculty, saveFaculty.getId());

        FacultyDTO getFaculty = facultyService.findOne(update.getId()).orElseThrow(NotFoundException::new);
        assertEquals(saveFaculty, update);
        assertEquals(UPDATE_FACULTY_NAME, getFaculty.getName());
        assertEquals(UPDATE_FACULTY_ABBREVIATION, getFaculty.getAbbreviation());
        assertEquals(UPDATE_START_REGISTRATION, getFaculty.getStartRegistration());
    }

    @Test
    public void deleteTest() {
        FacultyDTO faculty = saveFaculty(getFacultyObjectWithData());
        facultyService.delete(faculty.getId());
        assertThrows(NotFoundException.class, () -> facultyRepository.findById(faculty.getId()).orElseThrow(NotFoundException::new));
    }

    @Test
    public void deleteTest2() {
        assertThrows(BadRequestException.class, () -> facultyService.delete(99999999l));
    }

    @Test
    public void isBlockedRegistrationTest() {
        LocalDateTime newStartRegistration = LocalDateTime.now().withNano(0).plusDays(1);
        Faculty faculty = getFacultyObjectWithData();
        faculty.setStartRegistration(newStartRegistration);
        FacultyDTO saveFaculty = saveFaculty(faculty);

        ResponseEntity<?> blockedRegistration = facultyService.isBlockedRegistration(saveFaculty.getAbbreviation());
        assertEquals(newStartRegistration, blockedRegistration.getBody());
    }

    @Test
    public void isBlockedRegistrationTest2() {
        LocalDateTime newStartRegistration = LocalDateTime.now().withNano(0).minusDays(1);
        Faculty faculty = getFacultyObjectWithData();
        faculty.setStartRegistration(newStartRegistration);
        FacultyDTO saveFaculty = saveFaculty(faculty);

        ResponseEntity<?> blockedRegistration = facultyService.isBlockedRegistration(saveFaculty.getAbbreviation());
        assertEquals(false, blockedRegistration.getBody());
    }

    private Faculty getFacultyObjectWithData() {
        Faculty faculty = new Faculty();
        faculty.setName(DEFAULT_NAME);
        faculty.setAddress(DEFAULT_ADDRESS);
        faculty.setAbbreviation(DEFAULT_ABBREVIATION);
        faculty.setStartRegistration(DEFAULT_START_REGISTRATION);
        return faculty;
    }

    private FacultyDTO saveFaculty(Faculty facultyToSave) {
        FacultyDTO facultyDTO = facultyMapper.toDto(facultyToSave);
        FacultyDTO saveFaculty = facultyService.create(facultyDTO);
        return saveFaculty;
    }
}
