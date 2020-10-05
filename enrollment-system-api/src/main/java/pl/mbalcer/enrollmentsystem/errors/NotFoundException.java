package pl.mbalcer.enrollmentsystem.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {
    private final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public NotFoundException() {
        super("Not Found");
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
