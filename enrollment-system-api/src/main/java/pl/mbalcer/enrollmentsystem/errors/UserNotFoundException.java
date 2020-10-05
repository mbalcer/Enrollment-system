package pl.mbalcer.enrollmentsystem.errors;

public class UserNotFoundException extends NotFoundException {
    private static final String MESSAGE = "User not found!";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
