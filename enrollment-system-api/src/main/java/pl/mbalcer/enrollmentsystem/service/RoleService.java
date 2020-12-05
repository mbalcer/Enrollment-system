package pl.mbalcer.enrollmentsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.model.Role;
import pl.mbalcer.enrollmentsystem.model.enumeration.ERole;
import pl.mbalcer.enrollmentsystem.repository.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<ERole> getAllRoles() {
        log.debug("Request to get all roles");
        return roleRepository.findAll()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
