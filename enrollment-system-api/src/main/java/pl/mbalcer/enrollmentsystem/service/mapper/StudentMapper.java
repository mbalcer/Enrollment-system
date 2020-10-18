package pl.mbalcer.enrollmentsystem.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.mbalcer.enrollmentsystem.model.Student;
import pl.mbalcer.enrollmentsystem.model.dto.StudentDTO;

@Mapper(componentModel = "spring", uses = {FieldOfStudyMapper.class})
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {

    @Mapping(source = "fieldOfStudyDTO", target = "fieldOfStudy")
    Student toEntity(StudentDTO dto);

    @Mapping(source = "fieldOfStudy", target = "fieldOfStudyDTO")
    StudentDTO toDto(Student entity);
}
