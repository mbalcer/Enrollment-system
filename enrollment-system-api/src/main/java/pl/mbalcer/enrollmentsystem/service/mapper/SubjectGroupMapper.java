package pl.mbalcer.enrollmentsystem.service.mapper;


import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.mbalcer.enrollmentsystem.model.Student;
import pl.mbalcer.enrollmentsystem.model.SubjectGroup;
import pl.mbalcer.enrollmentsystem.model.Teacher;
import pl.mbalcer.enrollmentsystem.model.dto.SubjectGroupDTO;
import pl.mbalcer.enrollmentsystem.service.StudentService;
import pl.mbalcer.enrollmentsystem.service.TeacherService;

import java.util.Optional;

@Mapper(componentModel = "spring", uses = {FieldOfStudyMapper.class, StudentMapper.class, SubjectMapper.class, AppointmentMapper.class})
public abstract class SubjectGroupMapper implements EntityMapper<SubjectGroupDTO, SubjectGroup> {

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;

    @Mapping(target = "teacher", expression = "java(teacherFromFullName(dto.getNameTeacher()))")
    @Mapping(source = "fieldsOfStudyDTO", target = "fieldsOfStudy")
    @Mapping(source = "studentsDTO", target = "students")
    @Mapping(source = "subjectDTO", target = "subject")
    @Mapping(source = "timeTableDTO", target = "timeTable")
    @Mapping(source = "type", target = "type")
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
    @Mapping(target = "students", expression = "java(dto.getStudentsDTO().stream().map(studentDTO -> studentFromUsername(studentDTO.getUsername())). collect(java.util.stream.Collectors.toList()))")
    @Mapping(source = "subjectDTO", target = "subject")
    public abstract void updateSubjectGroup(SubjectGroupDTO dto, @MappingTarget SubjectGroup entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "students", expression = "java(dto.getStudentsDTO().stream().map(studentDTO -> studentFromUsername(studentDTO.getUsername())). collect(java.util.stream.Collectors.toList()))")
    public abstract void updateStudentsInGroup(SubjectGroupDTO dto, @MappingTarget SubjectGroup entity);

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

    Student studentFromUsername(String username) {
        Optional<Student> student = studentService.findOneByUsername(username);
        if (student.isPresent())
            return student.get();
        else {
            Student newStudent = new Student();
            newStudent.setUsername(username);
            return newStudent;
        }
    }
}
