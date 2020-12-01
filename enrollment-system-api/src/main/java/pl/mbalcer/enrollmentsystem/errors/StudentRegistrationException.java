package pl.mbalcer.enrollmentsystem.errors;

public class StudentRegistrationException extends BadRequestException {

    private static final String MESSAGE = "Error during student registration to group";

    public StudentRegistrationException() {
        super(MESSAGE);
    }

    public StudentRegistrationException(String message) {
        super(message);
    }
}
