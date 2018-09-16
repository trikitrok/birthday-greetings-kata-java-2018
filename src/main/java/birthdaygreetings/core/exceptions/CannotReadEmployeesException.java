package birthdaygreetings.core.exceptions;

public class CannotReadEmployeesException extends RuntimeException {
    public CannotReadEmployeesException(String cause, Exception exception) {
        super(cause, exception);
    }
}
