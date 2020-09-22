package pl.mbalcer.enrollmentsystem.errors;

public class LoginAlreadyUsedException extends BadRequestException {

    private static final String MESSAGE = "Login name already used!";

    public LoginAlreadyUsedException() {
        super(MESSAGE);
    }
}
