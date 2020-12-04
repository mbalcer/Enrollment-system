package pl.mbalcer.enrollmentsystem.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity handleEmailUsedException(EmailAlreadyUsedException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(ex);
    }

    @ExceptionHandler(LoginAlreadyUsedException.class)
    public ResponseEntity handleLoginUsedException(LoginAlreadyUsedException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(ex);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleBadRequestException(BadRequestException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(ex);
    }

    @ExceptionHandler(StudentRegistrationException.class)
    public ResponseEntity handleStudentRegistrationException(StudentRegistrationException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(ex);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserNotFoundException(UserNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity handleRoleNotFoundException(RoleNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex);
    }
}
