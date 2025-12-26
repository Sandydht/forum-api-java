package forum.api.java.commons.exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super("AUTHENTICATION_EXCEPTION." + message);
    }
}
