package pl.mbalcer.enrollmentsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.errors.BadRequestException;
import pl.mbalcer.enrollmentsystem.model.Subject;
import pl.mbalcer.enrollmentsystem.model.dto.SubjectDTO;
import pl.mbalcer.enrollmentsystem.repository.SubjectRepository;
import pl.mbalcer.enrollmentsystem.service.mapper.SubjectMapper;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SubjectService implements CrudService<SubjectDTO> {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    public SubjectService(SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    @Override
    public List<SubjectDTO> findAll() {
        log.debug("Request to get all Subjects");
        return subjectRepository.findAll()
                .stream()
                .map(subjectMapper::toDto)
                .sorted(Comparator.comparingLong(SubjectDTO::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SubjectDTO> findOne(Long id) {
        log.debug("Request to get Subject : {}", id);
        return subjectRepository.findById(id)
                .map(subjectMapper::toDto);
    }

    @Override
    public SubjectDTO create(SubjectDTO dto) {
        log.debug("Request to create Subject : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestException("A new Subject cannot already have an ID");
        }
        Subject subject = subjectMapper.toEntity(dto);
        subject = subjectRepository.save(subject);
        return subjectMapper.toDto(subject);
    }

    @Override
    public SubjectDTO update(SubjectDTO dto, Long id) {
        log.debug("Request to update Subject : {}", dto);
        if (id == null || !subjectRepository.existsById(id)) {
            throw new BadRequestException("Invalid id");
        }
        dto.setId(id);
        Subject subject = subjectMapper.toEntity(dto);
        subject = subjectRepository.save(subject);
        return subjectMapper.toDto(subject);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Subject : {}", id);
        Optional<Subject> subjectById = subjectRepository.findById(id);
        if (subjectById.isEmpty())
            throw new BadRequestException("Invalid id");
        // TODO remove foreign key to subject in subjectGroups
        subjectRepository.deleteById(id);
    }
}
