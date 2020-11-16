package pl.mbalcer.enrollmentsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mbalcer.enrollmentsystem.model.dto.request.LoginUserRequest;
import pl.mbalcer.enrollmentsystem.model.dto.request.RegisterUserRequest;
import pl.mbalcer.enrollmentsystem.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody RegisterUserRequest registerRequest) {
        return authService.registerUser(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity signIn(@RequestBody LoginUserRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }
}
