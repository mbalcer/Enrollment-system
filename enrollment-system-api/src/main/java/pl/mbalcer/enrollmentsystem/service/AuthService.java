package pl.mbalcer.enrollmentsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.errors.*;
import pl.mbalcer.enrollmentsystem.model.Role;
import pl.mbalcer.enrollmentsystem.model.Student;
import pl.mbalcer.enrollmentsystem.model.User;
import pl.mbalcer.enrollmentsystem.model.dto.request.ChangePasswordRequest;
import pl.mbalcer.enrollmentsystem.model.dto.request.LoginUserRequest;
import pl.mbalcer.enrollmentsystem.model.dto.request.RegisterUserRequest;
import pl.mbalcer.enrollmentsystem.model.dto.response.JwtResponse;
import pl.mbalcer.enrollmentsystem.model.dto.response.MessageResponse;
import pl.mbalcer.enrollmentsystem.model.enumeration.ERole;
import pl.mbalcer.enrollmentsystem.repository.RoleRepository;
import pl.mbalcer.enrollmentsystem.repository.StudentRepository;
import pl.mbalcer.enrollmentsystem.repository.UserRepository;
import pl.mbalcer.enrollmentsystem.security.jwt.JwtUtils;
import pl.mbalcer.enrollmentsystem.security.service.UserDetailsImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService {
    private final RoleRepository roleRepository;
    private final StudentRepository studentRepository;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthService(RoleRepository roleRepository, StudentRepository studentRepository, AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.roleRepository = roleRepository;
        this.studentRepository = studentRepository;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<?> authenticateUser(LoginUserRequest loginRequest) {
        log.debug("Authenticate user: {}", loginRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<ERole> roles = userDetails.getAuthorities().stream()
                .map(role -> ERole.valueOf(role.getAuthority()))
                .collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse(jwt,
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);

        return ResponseEntity.ok(jwtResponse);
    }

    public ResponseEntity<?> registerUser(RegisterUserRequest registerRequest) {
        log.debug("Register new user: {}", registerRequest.getUsername());
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UsernameAlreadyUsedException();
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyUsedException();
        }

        User user = new User(registerRequest.getUsername(),
                encoder.encode(registerRequest.getPassword()),
                registerRequest.getEmail());

        Set<Role> roles = new HashSet<>();
        Role studentRole = roleRepository.findByName(ERole.STUDENT).orElseThrow(RoleNotFoundException::new);
        roles.add(studentRole);

        user.setRoles(roles);
        user.setIsActive(true);

        Student student = new Student(user, 0l, 1, null, null);

        studentRepository.save(student);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity changePassword(ChangePasswordRequest passwordRequest) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!encoder.matches(passwordRequest.getOldPassword(), userPrincipal.getPassword())) {
            throw new InvalidPasswordException();
        }

        User user = userRepository.findUserByUsername(userPrincipal.getUsername()).orElseThrow(UserNotFoundException::new);
        log.debug("Request to change password for user: {}", user.getUsername());
        String encodeNewPassword = encoder.encode(passwordRequest.getNewPassword());
        user.setPassword(encodeNewPassword);

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Change password successfully!"));
    }
}
