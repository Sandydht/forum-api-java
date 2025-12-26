package forum.api.java.commons.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("NotFoundException")
public class NotFoundExceptionTest {
    @Test
    @DisplayName("should create NotFoundException correctly")
    public void testCreateAuthorizationExceptionCorrectly() {
        String message = "NOT_FOUND_ERROR";
        NotFoundException notFoundException = new NotFoundException(message);
        Assertions.assertEquals("NOT_FOUND_EXCEPTION." + message, notFoundException.getMessage());
    }
}
