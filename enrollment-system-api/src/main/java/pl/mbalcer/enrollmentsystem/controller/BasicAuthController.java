package pl.mbalcer.enrollmentsystem.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mbalcer.enrollmentsystem.model.AuthenticationBean;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class BasicAuthController {

    @GetMapping("/basic")
    public AuthenticationBean basicAuth() {
        return new AuthenticationBean("You are authenticated");
    }
}
