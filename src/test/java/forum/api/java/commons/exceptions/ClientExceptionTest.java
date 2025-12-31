package forum.api.java.commons.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ClientException")
public class ClientExceptionTest {
    @Test
    @DisplayName("should create ClientException with default status code 400")
    public void shouldCreateExceptionWithDefaultStatusCode() {
        String message = "CLIENT_ERROR";
        ClientException clientException = new ClientException(message);

        Assertions.assertEquals(message, clientException.getMessage());
        Assertions.assertEquals(400, clientException.getStatusCode());
        Assertions.assertInstanceOf(RuntimeException.class, clientException);
    }

    @Test
    @DisplayName("should create ClientException with custom status code")
    public void shouldCreateExceptionWithCustomStatusCode() {
        String errorMessage = "unauthorized access";
        int customStatusCode = 401;

        ClientException exception = new ClientException(errorMessage, customStatusCode);

        Assertions.assertEquals(errorMessage, exception.getMessage());
        Assertions.assertEquals(customStatusCode, exception.getStatusCode());
    }

    @Test
    @DisplayName("should be able to be thrown and caught")
    public void shouldBeAbleToBeThrown() {
        String msg = "resource not found";

        ClientException thrown = Assertions.assertThrows(ClientException.class, () -> {
            throw new ClientException(msg, 404);
        });

        Assertions.assertEquals(msg, thrown.getMessage());
        Assertions.assertEquals(404, thrown.getStatusCode());
    }
}
