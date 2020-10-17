package pl.mbalcer.enrollmentsystem.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.mbalcer.enrollmentsystem.model.Subject;
import pl.mbalcer.enrollmentsystem.model.dto.SubjectDTO;

@Mapper(componentModel = "spring", uses = {})
public interface SubjectMapper extends EntityMapper<SubjectDTO, Subject> {

    @Mapping(source = "id", target = "id")
    Subject toEntity(SubjectDTO dto);

    SubjectDTO toDto(Subject entity);
}
