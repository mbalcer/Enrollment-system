package pl.mbalcer.enrollmentsystem.service.mapper;

import org.mapstruct.*;
import pl.mbalcer.enrollmentsystem.model.Role;
import pl.mbalcer.enrollmentsystem.model.Student;
import pl.mbalcer.enrollmentsystem.model.dto.StudentDTO;
import pl.mbalcer.enrollmentsystem.model.enumeration.ERole;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {FieldOfStudyMapper.class})
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {

    @Mapping(source = "fieldOfStudyDTO", target = "fieldOfStudy")
    @Mapping(target = "roles", expression = "java(getRolesEntity(dto))")
    Student toEntity(StudentDTO dto);

    @Mapping(source = "fieldOfStudy", target = "fieldOfStudyDTO")
    @Mapping(target = "roles", expression = "java(getRolesEnum(entity))")
    StudentDTO toDto(Student entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "fieldOfStudyDTO", target = "fieldOfStudy")
    @Mapping(target = "roles", ignore = true)
    void updateStudent(StudentDTO dto, @MappingTarget Student entity);

    default Set<ERole> getRolesEnum(Student student) {
        return student.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    default Set<Role> getRolesEntity(StudentDTO studentDTO) {
        return studentDTO.getRoles()
                .stream()
                .map(r -> new Role(0l, r))
                .collect(Collectors.toSet());
    }
}
