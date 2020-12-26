package pl.mbalcer.enrollmentsystem.errors;

public class UsernameAlreadyUsedException extends BadRequestException {

    private static final String MESSAGE = "Username is already used!";

    public UsernameAlreadyUsedException() {
        super(MESSAGE);
    }
}
