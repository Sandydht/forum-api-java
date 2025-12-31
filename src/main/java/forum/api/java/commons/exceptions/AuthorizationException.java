package forum.api.java.commons.exceptions;

import org.springframework.http.HttpStatus;

public class AuthorizationException extends ClientException {
    public AuthorizationException(String message) {
        super(message, HttpStatus.FORBIDDEN.value());
    }
}
