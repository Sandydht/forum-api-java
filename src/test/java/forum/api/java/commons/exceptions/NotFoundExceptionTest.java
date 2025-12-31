package forum.api.java.commons.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("NotFoundException")
public class NotFoundExceptionTest {
    @Test
    @DisplayName("should create NotFoundException with correct message and default status 404")
    public void shouldCreateNotFoundExceptionCorrectly() {
        String errorMessage = "some error message";

        NotFoundException exception = new NotFoundException(errorMessage);

        Assertions.assertEquals(errorMessage, exception.getMessage());
        Assertions.assertEquals(404, exception.getStatusCode());
        Assertions.assertInstanceOf(ClientException.class, exception);
        Assertions.assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    @DisplayName("should be throwable as a ClientException")
    public void shouldBeThrowableAsClientException() {
        String errorMessage = "some error message";

        Assertions.assertThrows(ClientException.class, () -> {
            throw new NotFoundException(errorMessage);
        });
    }
}
