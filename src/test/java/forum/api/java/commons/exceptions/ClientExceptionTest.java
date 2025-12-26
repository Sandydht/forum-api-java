package forum.api.java.commons.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ClientException")
public class ClientExceptionTest {
    @Test
    @DisplayName("should create ClientException correctly")
    public void testCreateAuthorizationExceptionCorrectly() {
        String message = "CLIENT_ERROR";
        ClientException clientException = new ClientException(message);
        Assertions.assertEquals("CLIENT_EXCEPTION." + message, clientException.getMessage());
    }
}
