package pl.mbalcer.enrollmentsystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.mbalcer.enrollmentsystem.EnrollmentSystemApplication;
import pl.mbalcer.enrollmentsystem.model.Role;
import pl.mbalcer.enrollmentsystem.model.enumeration.ERole;
import pl.mbalcer.enrollmentsystem.repository.RoleRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EnrollmentSystemApplication.class)
public class RoleServiceTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @BeforeEach
    public void setUp() {
        roleRepository.deleteAll();
        roleRepository.save(new Role(0l, ERole.STUDENT));
        roleRepository.save(new Role(0l, ERole.ADMIN));
        roleRepository.save(new Role(0l, ERole.TEACHER));
    }

    @Test
    public void getAllRolesTest() {
        List<ERole> expected = new ArrayList<>(Arrays.asList(ERole.STUDENT, ERole.ADMIN, ERole.TEACHER));

        List<ERole> result = roleService.getAllRoles();

        assertIterableEquals(expected, result);
    }
}
