package pl.mbalcer.enrollmentsystem.service.mapper;

import org.mapstruct.Mapper;
import pl.mbalcer.enrollmentsystem.model.User;
import pl.mbalcer.enrollmentsystem.model.dto.UserDTO;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<UserDTO, User> {

    User toEntity(UserDTO dto);

    UserDTO toDto(User entity);
}
