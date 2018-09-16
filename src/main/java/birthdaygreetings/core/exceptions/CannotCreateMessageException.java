package birthdaygreetings.core.exceptions;

public class CannotCreateMessageException extends RuntimeException {
    public CannotCreateMessageException(String cause, Exception exception) {
        super(cause, exception);
    }
}