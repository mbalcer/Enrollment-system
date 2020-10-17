package pl.mbalcer.enrollmentsystem.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import pl.mbalcer.enrollmentsystem.model.Faculty;
import pl.mbalcer.enrollmentsystem.model.FieldOfStudy;
import pl.mbalcer.enrollmentsystem.model.dto.FieldOfStudyDTO;
import pl.mbalcer.enrollmentsystem.repository.FacultyRepository;

import java.util.Optional;

@Mapper(componentModel = "spring", uses = {})
public abstract class FieldOfStudyMapper implements EntityMapper<FieldOfStudyDTO, FieldOfStudy> {

    @Autowired
    FacultyRepository facultyRepository;

    @Mapping(target = "faculty", expression = "java(fromAbbreviation(dto.getAbbreviationFaculty()))")
    public abstract FieldOfStudy toEntity(FieldOfStudyDTO dto);

    @Mapping(source = "faculty.abbreviation", target = "abbreviationFaculty")
    public abstract FieldOfStudyDTO toDto(FieldOfStudy entity);

    Faculty fromAbbreviation(String abbreviation) {
        Optional<Faculty> faculty = facultyRepository.findByAbbreviation(abbreviation);
        if (faculty.isPresent())
            return faculty.get();
        else {
            Faculty newFaculty = new Faculty();
            newFaculty.setAbbreviation(abbreviation);
            return newFaculty;
        }
    }
}
