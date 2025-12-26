package forum.api.java.commons.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AuthorizationException")
public class AuthorizationExceptionTest {
    @Test
    @DisplayName("should create AuthorizationException correctly")
    public void testCreateAuthorizationExceptionCorrectly() {
        String message = "AUTHORIZATION_ERROR";
        AuthorizationException authorizationException = new AuthorizationException(message);
        Assertions.assertEquals("AUTHORIZATION_EXCEPTION." + message, authorizationException.getMessage());
    }
}
