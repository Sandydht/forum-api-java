package forum.api.java.commons.exceptions;

public class ClientException extends RuntimeException {
    public ClientException(String message) {
        super("CLIENT_EXCEPTION." + message);
    }
}
