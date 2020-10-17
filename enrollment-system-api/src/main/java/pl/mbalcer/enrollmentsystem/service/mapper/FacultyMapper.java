package pl.mbalcer.enrollmentsystem.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.mbalcer.enrollmentsystem.model.Faculty;
import pl.mbalcer.enrollmentsystem.model.dto.FacultyDTO;

@Mapper(componentModel = "spring", uses = {})
public interface FacultyMapper extends EntityMapper<FacultyDTO, Faculty> {

    @Mapping(source = "id", target = "id")
    Faculty toEntity(FacultyDTO dto);

    FacultyDTO toDto(Faculty entity);
}
