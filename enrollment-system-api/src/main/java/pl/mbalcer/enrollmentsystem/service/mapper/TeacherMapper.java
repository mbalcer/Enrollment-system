package pl.mbalcer.enrollmentsystem.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.mbalcer.enrollmentsystem.model.Teacher;
import pl.mbalcer.enrollmentsystem.model.dto.TeacherDTO;

@Mapper(componentModel = "spring", uses = {FacultyMapper.class})
public interface TeacherMapper extends EntityMapper<TeacherDTO, Teacher> {

    @Mapping(source = "facultyDTO", target = "faculty")
    Teacher toEntity(TeacherDTO dto);

    @Mapping(source = "faculty", target = "facultyDTO")
    TeacherDTO toDto(Teacher entity);
}
