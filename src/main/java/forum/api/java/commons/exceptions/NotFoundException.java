package forum.api.java.commons.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super("NOT_FOUND_EXCEPTION." + message);
    }
}
