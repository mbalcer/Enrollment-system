package pl.mbalcer.enrollmentsystem.service.mapper;

import org.mapstruct.*;
import pl.mbalcer.enrollmentsystem.model.Role;
import pl.mbalcer.enrollmentsystem.model.User;
import pl.mbalcer.enrollmentsystem.model.dto.UserDTO;
import pl.mbalcer.enrollmentsystem.model.enumeration.ERole;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<UserDTO, User> {

    @Mapping(source = "isActive", target = "isActive")
    @Mapping(target = "roles", expression = "java(getRolesEntity(dto))")
    User toEntity(UserDTO dto);

    @Mapping(target = "roles", expression = "java(getRolesEnum(entity))")
    UserDTO toDto(User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "roles", ignore = true)
    void updateUser(UserDTO dto, @MappingTarget User entity);

    default Set<ERole> getRolesEnum(User user) {
        return user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    default Set<Role> getRolesEntity(UserDTO userDTO) {
        return userDTO.getRoles()
                .stream()
                .map(r -> new Role(0l, r))
                .collect(Collectors.toSet());
    }
}
