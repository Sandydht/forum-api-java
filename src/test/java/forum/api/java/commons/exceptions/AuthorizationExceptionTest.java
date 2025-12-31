package forum.api.java.commons.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AuthorizationException")
public class AuthorizationExceptionTest {
    @Test
    @DisplayName("should create AuthorizationException with correct message and default status 403")
    public void shouldCreateAuthorizationExceptionCorrectly() {
        String errorMessage = "some error message";

        AuthorizationException exception = new AuthorizationException(errorMessage);

        Assertions.assertEquals(errorMessage, exception.getMessage());
        Assertions.assertEquals(403, exception.getStatusCode());
        Assertions.assertInstanceOf(ClientException.class, exception);
        Assertions.assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    @DisplayName("should be throwable as a ClientException")
    public void shouldBeThrowableAsClientException() {
        String errorMessage = "some error message";

        Assertions.assertThrows(ClientException.class, () -> {
            throw new AuthorizationException(errorMessage);
        });
    }
}
