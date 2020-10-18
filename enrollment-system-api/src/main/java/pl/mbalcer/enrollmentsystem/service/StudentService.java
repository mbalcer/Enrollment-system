package pl.mbalcer.enrollmentsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.errors.BadRequestException;
import pl.mbalcer.enrollmentsystem.errors.LoginAlreadyUsedException;
import pl.mbalcer.enrollmentsystem.model.Student;
import pl.mbalcer.enrollmentsystem.model.dto.StudentDTO;
import pl.mbalcer.enrollmentsystem.repository.StudentRepository;
import pl.mbalcer.enrollmentsystem.service.mapper.StudentMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentService implements CrudService<StudentDTO> {

    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;

    public StudentService(StudentMapper studentMapper, StudentRepository studentRepository) {
        this.studentMapper = studentMapper;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDTO> findAll() {
        log.debug("Request to get all Students");
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StudentDTO> findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        return studentRepository.findById(id)
                .map(studentMapper::toDto);
    }

    @Override
    public StudentDTO create(StudentDTO dto) {
        log.debug("Request to create Student : {}", dto);
        Student student = studentMapper.toEntity(dto);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    @Override
    public StudentDTO update(StudentDTO dto, Long id) {
        log.debug("Request to update Student : {}", dto);
        if (id == null || !studentRepository.existsById(id)) {
            throw new BadRequestException("Invalid id");
        }
        Student student = studentMapper.toEntity(dto);
        student.setId(id);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        Optional<Student> studentById = studentRepository.findById(id);
        if (studentById.isEmpty())
            throw new BadRequestException("Invalid id");
        studentRepository.deleteById(id);
    }

    public StudentDTO findOneByUsername(String username) {
        log.debug("Request to get Student by username: {}", username);
        Student student = studentRepository.findByUsername(username).orElseThrow(LoginAlreadyUsedException::new);
        return studentMapper.toDto(student);
    }
}
