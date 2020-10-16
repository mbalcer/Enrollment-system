package pl.mbalcer.enrollmentsystem.service.mapper;

import org.mapstruct.Mapper;
import pl.mbalcer.enrollmentsystem.model.Faculty;
import pl.mbalcer.enrollmentsystem.model.dto.FacultyDTO;

@Mapper(componentModel = "spring", uses = {})
public interface FacultyMapper extends EntityMapper<FacultyDTO, Faculty> {

    Faculty toEntity(FacultyDTO dto);

    FacultyDTO toDto(Faculty entity);
}
