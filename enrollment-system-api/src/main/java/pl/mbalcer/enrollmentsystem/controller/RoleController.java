package pl.mbalcer.enrollmentsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mbalcer.enrollmentsystem.model.enumeration.ERole;
import pl.mbalcer.enrollmentsystem.service.RoleService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/roles")
@Slf4j
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<ERole>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}
