package forum.api.java.commons.exceptions;

import org.springframework.http.HttpStatus;

public class InvariantException extends ClientException {
    public InvariantException(String message) {
        super(message, HttpStatus.BAD_REQUEST.value());
    }
}
