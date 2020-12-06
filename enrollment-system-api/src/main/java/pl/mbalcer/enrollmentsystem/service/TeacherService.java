package pl.mbalcer.enrollmentsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.errors.BadRequestException;
import pl.mbalcer.enrollmentsystem.errors.NotFoundException;
import pl.mbalcer.enrollmentsystem.errors.UserNotFoundException;
import pl.mbalcer.enrollmentsystem.model.Teacher;
import pl.mbalcer.enrollmentsystem.model.dto.TeacherDTO;
import pl.mbalcer.enrollmentsystem.repository.TeacherRepository;
import pl.mbalcer.enrollmentsystem.service.mapper.TeacherMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TeacherService implements CrudService<TeacherDTO> {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public List<TeacherDTO> findAll() {
        log.debug("Request to get all Teachers");
        return teacherRepository.findAll()
                .stream()
                .map(teacherMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TeacherDTO> findOne(Long id) {
        log.debug("Request to get Teacher : {}" + id);
        return teacherRepository.findById(id)
                .map(teacherMapper::toDto);
    }

    @Override
    public TeacherDTO create(TeacherDTO dto) {
        log.debug("Request to create Teacher : {}", dto);
        Teacher teacher = teacherMapper.toEntity(dto);
        teacher = teacherRepository.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    @Override
    public TeacherDTO update(TeacherDTO dto, Long id) {
        log.debug("Request to update Teacher : {}", dto);
        if (id == null || !teacherRepository.existsById(id)) {
            throw new BadRequestException("Invalid id");
        }
        Teacher teacher = teacherMapper.toEntity(dto);
        teacher.setId(id);
        teacher = teacherRepository.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    public TeacherDTO updateByUsername(TeacherDTO dto, String username) {
        log.debug("Request to update Teacher by username: {}", dto);
        Teacher teacher = teacherRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        teacherMapper.updateTeacher(dto, teacher);
        teacher = teacherRepository.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Teacher : {}", id);
        Optional<Teacher> teacherById = teacherRepository.findById(id);
        if (teacherById.isEmpty())
            throw new BadRequestException("Invalid id");
        teacherRepository.deleteById(id);
    }

    public TeacherDTO findOneByUsername(String username) {
        log.debug("Request to get Teacher by username: {}", username);
        Teacher teacher = teacherRepository.findByUsername(username).orElseThrow(NotFoundException::new);
        return teacherMapper.toDto(teacher);
    }

    public Optional<Teacher> findOneByFullName(String fullName) {
        log.debug("Request to get Teacher by fullName: {}", fullName);
        return teacherRepository.findByFullName(fullName);
    }
}
