package pl.mbalcer.enrollmentsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.errors.BadRequestException;
import pl.mbalcer.enrollmentsystem.model.FieldOfStudy;
import pl.mbalcer.enrollmentsystem.model.dto.FieldOfStudyDTO;
import pl.mbalcer.enrollmentsystem.repository.FieldOfStudyRepository;
import pl.mbalcer.enrollmentsystem.service.mapper.FieldOfStudyMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FieldOfStudyService implements CrudService<FieldOfStudyDTO> {

    private FieldOfStudyRepository fieldOfStudyRepository;
    private FieldOfStudyMapper fieldOfStudyMapper;

    public FieldOfStudyService(FieldOfStudyRepository fieldOfStudyRepository, FieldOfStudyMapper fieldOfStudyMapper) {
        this.fieldOfStudyRepository = fieldOfStudyRepository;
        this.fieldOfStudyMapper = fieldOfStudyMapper;
    }

    @Override
    public List<FieldOfStudyDTO> findAll() {
        log.debug("Request to get all FieldsOfStudy");
        return fieldOfStudyRepository.findAll()
                .stream()
                .map(fieldOfStudyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FieldOfStudyDTO> findOne(Long id) {
        log.debug("Request to get FieldOfStudy : {}", id);
        return fieldOfStudyRepository.findById(id)
                .map(fieldOfStudyMapper::toDto);
    }

    @Override
    public FieldOfStudyDTO create(FieldOfStudyDTO dto) {
        log.debug("Request to create FieldOfStudy : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestException("A new faculty cannot already have an ID");
        }
        FieldOfStudy fieldOfStudy = fieldOfStudyMapper.toEntity(dto);
        fieldOfStudy = fieldOfStudyRepository.save(fieldOfStudy);
        return fieldOfStudyMapper.toDto(fieldOfStudy);
    }

    @Override
    public FieldOfStudyDTO update(FieldOfStudyDTO dto, Long id) {
        log.debug("Request to update FieldOfStudy : {}", dto);
        if (id == null || !fieldOfStudyRepository.existsById(id)) {
            throw new BadRequestException("Invalid id");
        }
        dto.setId(id);
        FieldOfStudy fieldOfStudy = fieldOfStudyMapper.toEntity(dto);
        fieldOfStudy = fieldOfStudyRepository.save(fieldOfStudy);
        return fieldOfStudyMapper.toDto(fieldOfStudy);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FieldOfStudy : {}", id);
        Optional<FieldOfStudy> fieldOfStudyById = fieldOfStudyRepository.findById(id);
        if (fieldOfStudyById.isEmpty())
            throw new BadRequestException("Invalid id");
        // TODO remove foreign key to fieldofstudy in subjectgroups
        fieldOfStudyRepository.deleteById(id);
    }
}
