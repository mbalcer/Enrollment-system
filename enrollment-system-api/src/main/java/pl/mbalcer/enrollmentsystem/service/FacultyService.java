package pl.mbalcer.enrollmentsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.errors.BadRequestException;
import pl.mbalcer.enrollmentsystem.model.Faculty;
import pl.mbalcer.enrollmentsystem.model.dto.FacultyDTO;
import pl.mbalcer.enrollmentsystem.repository.FacultyRepository;
import pl.mbalcer.enrollmentsystem.service.mapper.FacultyMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FacultyService implements CrudService<FacultyDTO> {

    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;

    public FacultyService(FacultyRepository facultyRepository, FacultyMapper facultyMapper) {
        this.facultyRepository = facultyRepository;
        this.facultyMapper = facultyMapper;
    }

    @Override
    public List<FacultyDTO> findAll() {
        log.debug("Request to get all Faculties");
        return facultyRepository.findAll()
                .stream()
                .map(facultyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FacultyDTO> findOne(Long id) {
        log.debug("Request to get Faculty : {}", id);
        return facultyRepository.findById(id)
                .map(facultyMapper::toDto);
    }

    @Override
    public FacultyDTO create(FacultyDTO dto) {
        log.debug("Request to create Faculty : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestException("A new faculty cannot already have an ID");
        }
        Faculty faculty = facultyMapper.toEntity(dto);
        faculty = facultyRepository.save(faculty);
        return facultyMapper.toDto(faculty);
    }

    @Override
    public FacultyDTO update(FacultyDTO dto, Long id) {
        log.debug("Request to update Faculty : {}", dto);
        if (id == null || !facultyRepository.existsById(id)) {
            throw new BadRequestException("Invalid id");
        }
        dto.setId(id);
        Faculty faculty = facultyMapper.toEntity(dto);
        faculty = facultyRepository.save(faculty);
        return facultyMapper.toDto(faculty);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Faculty : {}", id);
        Optional<Faculty> facultyById = facultyRepository.findById(id);
        if (facultyById.isEmpty())
            throw new BadRequestException("Invalid id");
        // TODO remove foreign key to faculty in fieldofstudy and teacher
        facultyRepository.deleteById(id);
    }

    public Optional<Faculty> findOneByAbbreviation(String abbreviation) {
        log.debug("Request to get Faculty by abbreviation : {}", abbreviation);
        return facultyRepository.findByAbbreviation(abbreviation);
    }

    public ResponseEntity<?> isBlockedRegistration(String abbreviation) {
        log.debug("Request to check if registration is blocked");
        Optional<Faculty> optionalFaculty = findOneByAbbreviation(abbreviation);
        if (optionalFaculty.isPresent()) {
            Faculty faculty = optionalFaculty.get();
            if (faculty.getStartRegistration() != null && faculty.getStartRegistration().isAfter(LocalDateTime.now())) {
                return ResponseEntity.ok(faculty.getStartRegistration());
            }
        }

        return ResponseEntity.ok(false);
    }
}
