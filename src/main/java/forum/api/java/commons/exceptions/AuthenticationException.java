package forum.api.java.commons.exceptions;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends ClientException {
    public AuthenticationException(String message) {
        super(message, HttpStatus.UNAUTHORIZED.value());
    }
}
