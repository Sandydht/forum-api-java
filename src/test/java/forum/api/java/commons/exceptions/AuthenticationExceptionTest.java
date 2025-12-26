package forum.api.java.commons.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AuthenticationException")
public class AuthenticationExceptionTest {
    @Test
    @DisplayName("should create AuthenticationException correctly")
    public void testCreateAuthenticationExceptionCorrectly() {
        String message = "AUTHENTICATION_ERROR";
        AuthenticationException authenticationException = new AuthenticationException(message);
        Assertions.assertEquals("AUTHENTICATION_EXCEPTION." + message, authenticationException.getMessage());
    }
}
