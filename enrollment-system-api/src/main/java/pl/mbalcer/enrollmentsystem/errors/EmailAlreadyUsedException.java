package pl.mbalcer.enrollmentsystem.errors;

public class EmailAlreadyUsedException extends BadRequestException {

    private static final String MESSAGE = "Email is already in use!";

    public EmailAlreadyUsedException() {
        super(MESSAGE);
    }
}
