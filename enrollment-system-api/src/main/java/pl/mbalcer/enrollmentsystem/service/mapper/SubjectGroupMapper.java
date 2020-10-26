package pl.mbalcer.enrollmentsystem.service.mapper;


import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.mbalcer.enrollmentsystem.model.SubjectGroup;
import pl.mbalcer.enrollmentsystem.model.dto.SubjectGroupDTO;

@Mapper(componentModel = "spring", uses = {FieldOfStudyMapper.class, StudentMapper.class, SubjectMapper.class, AppointmentMapper.class})
public interface SubjectGroupMapper extends EntityMapper<SubjectGroupDTO, SubjectGroup> {

    @Mapping(source = "nameTeacher", target = "teacher.fullName")
    @Mapping(source = "fieldsOfStudyDTO", target = "fieldsOfStudy")
    @Mapping(source = "studentsDTO", target = "students")
    @Mapping(source = "subjectDTO", target = "subject")
    @Mapping(source = "timeTableDTO", target = "timeTable")
    SubjectGroup toEntity(SubjectGroupDTO dto);

    @Mapping(source = "teacher.fullName", target = "nameTeacher")
    @Mapping(source = "fieldsOfStudy", target = "fieldsOfStudyDTO")
    @Mapping(source = "students", target = "studentsDTO")
    @Mapping(source = "subject", target = "subjectDTO")
    @Mapping(source = "timeTable", target = "timeTableDTO")
    @Named("toDto")
    SubjectGroupDTO toDto(SubjectGroup entity);

    @InheritConfiguration(name = "toDto")
    @Mapping(target = "studentsDTO", ignore = true)
    SubjectGroupDTO toDTOWithoutStudents(SubjectGroup subjectGroup);
}
