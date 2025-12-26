package forum.api.java.commons.exceptions;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message) {
        super("AUTHORIZATION_EXCEPTION." + message);
    }
}
