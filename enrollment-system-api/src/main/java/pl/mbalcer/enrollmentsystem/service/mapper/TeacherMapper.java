package pl.mbalcer.enrollmentsystem.service.mapper;

import org.mapstruct.*;
import pl.mbalcer.enrollmentsystem.model.Role;
import pl.mbalcer.enrollmentsystem.model.Teacher;
import pl.mbalcer.enrollmentsystem.model.dto.TeacherDTO;
import pl.mbalcer.enrollmentsystem.model.enumeration.ERole;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {FacultyMapper.class})
public interface TeacherMapper extends EntityMapper<TeacherDTO, Teacher> {

    @Mapping(source = "facultyDTO", target = "faculty")
    @Mapping(target = "roles", expression = "java(getRolesEntity(dto))")
    Teacher toEntity(TeacherDTO dto);

    @Mapping(source = "faculty", target = "facultyDTO")
    @Mapping(target = "roles", expression = "java(getRolesEnum(entity))")
    TeacherDTO toDto(Teacher entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "facultyDTO", target = "faculty")
    @Mapping(target = "roles", ignore = true)
    void updateTeacher(TeacherDTO dto, @MappingTarget Teacher entity);

    default Set<ERole> getRolesEnum(Teacher teacher) {
        return teacher.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    default Set<Role> getRolesEntity(TeacherDTO teacherDTO) {
        return teacherDTO.getRoles()
                .stream()
                .map(r -> new Role(0l, r))
                .collect(Collectors.toSet());
    }
}
