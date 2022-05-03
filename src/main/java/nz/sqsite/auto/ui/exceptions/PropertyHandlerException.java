package nz.sqsite.auto.ui.exceptions;

public class PropertyHandlerException extends RuntimeException {
    public PropertyHandlerException(String message) {
        super(message);
    }

    public PropertyHandlerException(Throwable cause) {
        super(cause);
    }

    public PropertyHandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
