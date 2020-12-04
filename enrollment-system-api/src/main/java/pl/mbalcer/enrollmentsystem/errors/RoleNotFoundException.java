package pl.mbalcer.enrollmentsystem.errors;

public class RoleNotFoundException extends NotFoundException {
    private static final String MESSAGE = "Role is not found!";

    public RoleNotFoundException() {
        super(MESSAGE);
    }
}
