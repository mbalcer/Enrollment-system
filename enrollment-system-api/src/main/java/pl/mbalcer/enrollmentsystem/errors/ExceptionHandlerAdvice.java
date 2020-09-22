package pl.mbalcer.enrollmentsystem.errors;

import lombok.extern.slf4j.Slf4j;
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
}
