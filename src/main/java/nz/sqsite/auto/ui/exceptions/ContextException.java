package nz.sqsite.auto.ui.exceptions;

public class ContextException extends RuntimeException {
    public ContextException(String message) {
        super(message);
    }

    public ContextException(Throwable cause) {
        super(cause);
    }

    public ContextException(String message, Throwable cause) {
        super(message, cause);
    }
}
