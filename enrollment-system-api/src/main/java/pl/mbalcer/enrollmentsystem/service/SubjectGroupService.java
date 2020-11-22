package pl.mbalcer.enrollmentsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.errors.BadRequestException;
import pl.mbalcer.enrollmentsystem.model.SubjectGroup;
import pl.mbalcer.enrollmentsystem.model.dto.SubjectGroupDTO;
import pl.mbalcer.enrollmentsystem.model.enumeration.GroupType;
import pl.mbalcer.enrollmentsystem.repository.AppointmentRepository;
import pl.mbalcer.enrollmentsystem.repository.SubjectGroupRepository;
import pl.mbalcer.enrollmentsystem.service.mapper.AppointmentMapper;
import pl.mbalcer.enrollmentsystem.service.mapper.SubjectGroupMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SubjectGroupService implements CrudService<SubjectGroupDTO> {

    private final SubjectGroupRepository subjectGroupRepository;
    private final SubjectGroupMapper subjectGroupMapper;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    public SubjectGroupService(SubjectGroupRepository subjectGroupRepository, SubjectGroupMapper subjectGroupMapper, AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper) {
        this.subjectGroupRepository = subjectGroupRepository;
        this.subjectGroupMapper = subjectGroupMapper;
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public List<SubjectGroupDTO> findAll() {
        log.debug("Request to get all SubjectsGroup");
        return subjectGroupRepository.findAll()
                .stream()
                .map(subjectGroupMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SubjectGroupDTO> findAllByTeacher(String teacher) {
        log.debug("Request to get all SubjectsGroup by Teacher: {}", teacher);
        return subjectGroupRepository.findAllByTeacherUsername(teacher)
                .stream()
                .map(subjectGroupMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SubjectGroupDTO> findAllByType(GroupType type) {
        log.debug("Request to get all SubjectGroup by type: {}", type);
        return subjectGroupRepository.findAllByType(type)
                .stream()
                .map(subjectGroupMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SubjectGroupDTO> findAllRegistration(Long id) {
        log.debug("Request to get all SubjectGroup for registration by FieldOfStudy id: {}", id);
        return findAllByType(GroupType.ACCEPTED)
                .stream()
                .filter(group -> group.getFieldsOfStudyDTO()
                        .stream()
                        .anyMatch(fieldOfStudy -> fieldOfStudy.getId() == id))
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
        saveTimeTable(group);
        return subjectGroupMapper.toDto(group);
    }

    @Override
    public SubjectGroupDTO update(SubjectGroupDTO dto, Long id) {
        log.debug("Request to update SubjectGroup : {}", dto);
        if (id == null || !subjectGroupRepository.existsById(id)) {
            throw new BadRequestException("Invalid id");
        }
        SubjectGroup group = subjectGroupRepository.findById(id).get();
        group.getTimeTable().forEach(appointment -> {
            appointment.setGroup(null);
            appointmentRepository.deleteById(appointment.getId());
        });
        subjectGroupMapper.updateSubjectGroup(dto, group);
        appointmentMapper.updateAppointments(dto.getTimeTableDTO(), group.getTimeTable());
        saveTimeTable(group);
        group = subjectGroupRepository.save(group);
        return subjectGroupMapper.toDto(group);
    }

    public SubjectGroupDTO updateStudents(SubjectGroupDTO dto) {
        log.debug("Request to update students list in SubjectGroup : {}", dto);
        if (!subjectGroupRepository.existsById(dto.getId())) {
            throw new BadRequestException("Invalid id");
        }
        SubjectGroup group = subjectGroupRepository.findById(dto.getId()).get();
        subjectGroupMapper.updateSubjectGroup(dto, group);
        if (group.getStudents().size()==group.getNumberOfPlaces()) {
            group.setType(GroupType.FULL);
        } else {
            group.setType(GroupType.ACCEPTED);
        }
        group = subjectGroupRepository.save(group);

        return subjectGroupMapper.toDto(group);
    }

    private void saveTimeTable(SubjectGroup group) {
        Optional.ofNullable(group.getTimeTable())
                .orElse(Collections.emptyList())
                .forEach(appointment -> {
                    appointment.setGroup(group);
                    appointmentRepository.save(appointment);
                });
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubjectGroup : {}", id);
        Optional<SubjectGroup> groupById = subjectGroupRepository.findById(id);
        if (groupById.isEmpty())
            throw new BadRequestException("Invalid id");
        subjectGroupRepository.deleteById(id);
    }

    public SubjectGroupDTO updateGroupType(GroupType groupType, Long id) {
        log.debug("Request to update group type in SubjectGroup: {}", id);
        Optional<SubjectGroup> groupOptional = subjectGroupRepository.findById(id);
        if (groupOptional.isEmpty())
            throw new BadRequestException("Group by ID " + id + " not found");
        groupOptional.get().setType(groupType);
        SubjectGroup saveGroup = subjectGroupRepository.save(groupOptional.get());

        return subjectGroupMapper.toDto(saveGroup);
    }
}
