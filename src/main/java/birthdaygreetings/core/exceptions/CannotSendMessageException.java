package birthdaygreetings.core.exceptions;

public class CannotSendMessageException extends RuntimeException {
    public CannotSendMessageException(String cause, Exception exception) {
        super(cause, exception);
    }
}