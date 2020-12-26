package pl.mbalcer.enrollmentsystem.errors;

public class InvalidPasswordException extends BadRequestException {

    private static final String MESSAGE = "Invalid password!";

    public InvalidPasswordException() {
        super(MESSAGE);
    }
}
