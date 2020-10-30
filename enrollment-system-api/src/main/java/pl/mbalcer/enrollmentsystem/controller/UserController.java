package pl.mbalcer.enrollmentsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mbalcer.enrollmentsystem.model.dto.UserDTO;
import pl.mbalcer.enrollmentsystem.service.UserService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.debug("REST request to get all Users");
        List<UserDTO> all = userService.findAll();
        return ResponseEntity.ok(all);
    }

    @PatchMapping("/role")
    public ResponseEntity<UserDTO> changeRole(@RequestBody UserDTO userDTO) {
        log.debug("REST request to change role for user: " + userDTO.getUsername());
        return ResponseEntity.ok(userService.changeRole(userDTO));
    }
}
