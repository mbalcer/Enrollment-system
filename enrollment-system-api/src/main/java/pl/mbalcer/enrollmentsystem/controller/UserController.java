package pl.mbalcer.enrollmentsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mbalcer.enrollmentsystem.model.Role;
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
        List<UserDTO> all = userService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{username}")
    public ResponseEntity getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @PutMapping("/user/{username}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable String username) {
        UserDTO result = userService.update(userDTO, username);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/role/add/{username}")
    public ResponseEntity<UserDTO> addRoleToUser(@RequestBody Role role, @PathVariable String username) {
        UserDTO userDTO = userService.addRole(username, role.getName());
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/role/remove/{username}")
    public ResponseEntity<UserDTO> removeRoleFromUser(@RequestBody Role role, @PathVariable String username) {
        UserDTO userDTO = userService.removeRole(username, role.getName());
        return ResponseEntity.ok(userDTO);
    }
}
