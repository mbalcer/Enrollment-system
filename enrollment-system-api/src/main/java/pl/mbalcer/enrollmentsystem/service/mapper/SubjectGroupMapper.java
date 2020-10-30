package pl.mbalcer.enrollmentsystem.service.mapper;


import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.mbalcer.enrollmentsystem.model.SubjectGroup;
import pl.mbalcer.enrollmentsystem.model.Teacher;
import pl.mbalcer.enrollmentsystem.model.dto.SubjectGroupDTO;
import pl.mbalcer.enrollmentsystem.service.TeacherService;

import java.util.Optional;

@Mapper(componentModel = "spring", uses = {FieldOfStudyMapper.class, StudentMapper.class, SubjectMapper.class, AppointmentMapper.class})
public abstract class SubjectGroupMapper implements EntityMapper<SubjectGroupDTO, SubjectGroup> {

    @Autowired
    TeacherService teacherService;

    @Mapping(target = "teacher", expression = "java(teacherFromFullName(dto.getNameTeacher()))")
    @Mapping(source = "fieldsOfStudyDTO", target = "fieldsOfStudy")
    @Mapping(source = "studentsDTO", target = "students")
    @Mapping(source = "subjectDTO", target = "subject")
    @Mapping(source = "timeTableDTO", target = "timeTable")
    public abstract SubjectGroup toEntity(SubjectGroupDTO dto);

    @Mapping(source = "teacher.fullName", target = "nameTeacher")
    @Mapping(source = "fieldsOfStudy", target = "fieldsOfStudyDTO")
    @Mapping(source = "students", target = "studentsDTO")
    @Mapping(source = "subject", target = "subjectDTO")
    @Mapping(source = "timeTable", target = "timeTableDTO")
    @Named("toDto")
    public abstract SubjectGroupDTO toDto(SubjectGroup entity);

    @InheritConfiguration(name = "toDto")
    @Mapping(target = "studentsDTO", ignore = true)
    public abstract SubjectGroupDTO toDTOWithoutStudents(SubjectGroup subjectGroup);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "teacher", expression = "java(teacherFromFullName(dto.getNameTeacher()))")
    @Mapping(source = "fieldsOfStudyDTO", target = "fieldsOfStudy")
    @Mapping(source = "studentsDTO", target = "students")
    @Mapping(source = "subjectDTO", target = "subject")
    public abstract void updateSubjectGroup(SubjectGroupDTO dto, @MappingTarget SubjectGroup entity);

    Teacher teacherFromFullName(String fullName) {
        Optional<Teacher> teacher = teacherService.findOneByFullName(fullName);
        if (teacher.isPresent())
            return teacher.get();
        else  {
            Teacher newTeacher = new Teacher();
            newTeacher.setFullName(fullName);
            return newTeacher;
        }
    }
}
