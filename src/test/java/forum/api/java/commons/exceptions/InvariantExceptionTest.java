package forum.api.java.commons.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("InvariantException")
public class InvariantExceptionTest {
    @Test
    @DisplayName("should create InvariantException with correct message and default status 400")
    public void shouldCreateInvariantExceptionCorrectly() {
        String errorMessage = "some error message";

        InvariantException exception = new InvariantException(errorMessage);

        Assertions.assertEquals(errorMessage, exception.getMessage());
        Assertions.assertEquals(400, exception.getStatusCode());
        Assertions.assertInstanceOf(ClientException.class, exception);
        Assertions.assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    @DisplayName("should be throwable as a ClientException")
    public void shouldBeThrowableAsClientException() {
        String errorMessage = "some error message";

        Assertions.assertThrows(ClientException.class, () -> {
            throw new InvariantException(errorMessage);
        });
    }
}
