package pl.mbalcer.enrollmentsystem.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.mbalcer.enrollmentsystem.model.Appointment;
import pl.mbalcer.enrollmentsystem.model.dto.AppointmentDTO;

@Mapper(componentModel = "spring", uses = {})
public interface AppointmentMapper extends EntityMapper<AppointmentDTO, Appointment> {
    @Mapping(source = "startTime", target = "startTime")
    Appointment toEntity(AppointmentDTO dto);

    AppointmentDTO toDto(Appointment entity);
}
