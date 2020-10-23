package pl.mbalcer.enrollmentsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.errors.BadRequestException;
import pl.mbalcer.enrollmentsystem.model.SubjectGroup;
import pl.mbalcer.enrollmentsystem.model.dto.SubjectGroupDTO;
import pl.mbalcer.enrollmentsystem.repository.SubjectGroupRepository;
import pl.mbalcer.enrollmentsystem.service.mapper.SubjectGroupMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SubjectGroupService implements CrudService<SubjectGroupDTO> {

    private final SubjectGroupRepository subjectGroupRepository;
    private final SubjectGroupMapper subjectGroupMapper;

    public SubjectGroupService(SubjectGroupRepository subjectGroupRepository, SubjectGroupMapper subjectGroupMapper) {
        this.subjectGroupRepository = subjectGroupRepository;
        this.subjectGroupMapper = subjectGroupMapper;
    }

    @Override
    public List<SubjectGroupDTO> findAll() {
        log.debug("Request to get all SubjectsGroup");
        return subjectGroupRepository.findAll()
                .stream()
                .map(subjectGroupMapper::toDTOWithoutStudents)
                .collect(Collectors.toList());
    }

    public List<SubjectGroupDTO> findAllByTeacher(String teacher) {
        log.debug("Request to get all SubjectsGroup by Teacher");
        return subjectGroupRepository.findAllByTeacherUsername(teacher)
                .stream()
                .map(subjectGroupMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SubjectGroupDTO> findOne(Long id) {
        log.debug("Request to get SubjectGroup : {}", id);
        return subjectGroupRepository.findById(id)
                .map(subjectGroupMapper::toDto);
    }

    @Override
    public SubjectGroupDTO create(SubjectGroupDTO dto) {
        log.debug("Request to create SubjectGroup : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestException("A new subjectGroup cannot already have an ID");
        }
        SubjectGroup group = subjectGroupMapper.toEntity(dto);
        group = subjectGroupRepository.save(group);
        return subjectGroupMapper.toDto(group);
    }

    @Override
    public SubjectGroupDTO update(SubjectGroupDTO dto, Long id) {
        log.debug("Request to update SubjectGroup : {}", dto);
        if (id == null || !subjectGroupRepository.existsById(id)) {
            throw new BadRequestException("Invalid id");
        }
        SubjectGroup group = subjectGroupMapper.toEntity(dto);
        group.setId(id);
        group = subjectGroupRepository.save(group);
        return subjectGroupMapper.toDto(group);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubjectGroup : {}", id);
        Optional<SubjectGroup> groupById = subjectGroupRepository.findById(id);
        if (groupById.isEmpty())
            throw new BadRequestException("Invalid id");
        subjectGroupRepository.deleteById(id);
    }
}