package pl.mbalcer.enrollmentsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mbalcer.enrollmentsystem.model.AuthenticationBean;
import pl.mbalcer.enrollmentsystem.model.dto.RegisterUserDTO;
import pl.mbalcer.enrollmentsystem.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AccountController {

    private UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth")
    public AuthenticationBean basicAuth() {
        return new AuthenticationBean("You are authenticated");
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody RegisterUserDTO userDTO) {
        return userService.registerUser(userDTO);
    }
}
