package pl.mbalcer.enrollmentsystem.service.mapper;

import org.mapstruct.*;
import pl.mbalcer.enrollmentsystem.model.Appointment;
import pl.mbalcer.enrollmentsystem.model.dto.AppointmentDTO;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface AppointmentMapper extends EntityMapper<AppointmentDTO, Appointment> {
    @Mapping(source = "startTime", target = "startTime")
    Appointment toEntity(AppointmentDTO dto);

    AppointmentDTO toDto(Appointment entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAppointments(List<AppointmentDTO> dto, @MappingTarget List<Appointment> entity);

}
