package pl.mbalcer.enrollmentsystem.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.mbalcer.enrollmentsystem.model.Subject;
import pl.mbalcer.enrollmentsystem.model.dto.SubjectDTO;

import java.time.Duration;

@Mapper(componentModel = "spring", uses = {})
public interface SubjectMapper extends EntityMapper<SubjectDTO, Subject> {

    @Mapping(target = "numberOfHours", expression = "java(mapFromLongToDuration(dto.getNumberOfHours()))")
    Subject toEntity(SubjectDTO dto);

    @Mapping(target = "numberOfHours", expression = "java(entity.getNumberOfHours().toHours())")
    SubjectDTO toDto(Subject entity);

    default Duration mapFromLongToDuration(Long value) {
        return Duration.ofHours(value);
    }
}
