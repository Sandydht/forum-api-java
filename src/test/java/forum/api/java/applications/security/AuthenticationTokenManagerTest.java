package forum.api.java.applications.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AuthenticationTokenManager interface")
public class AuthenticationTokenManagerTest {
    private final AuthenticationTokenManager authenticationTokenManager = new AuthenticationTokenManager() {};

    @Test
    @DisplayName("should throw error when invoke unimplemented method")
    public void testThrowErrorWhenInvokeAbstractBehavior() {
        UnsupportedOperationException createAccessTokenError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> authenticationTokenManager.createAccessToken(null)
        );
        Assertions.assertEquals("AUTHENTICATION_TOKEN_MANAGER.METHOD_NOT_IMPLEMENTED", createAccessTokenError.getMessage());

        UnsupportedOperationException createRefreshTokenError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> authenticationTokenManager.createRefreshToken(null)
        );
        Assertions.assertEquals("AUTHENTICATION_TOKEN_MANAGER.METHOD_NOT_IMPLEMENTED", createRefreshTokenError.getMessage());

        UnsupportedOperationException decodeJWTPayloadError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> authenticationTokenManager.decodeJWTPayload(null)
        );
        Assertions.assertEquals("AUTHENTICATION_TOKEN_MANAGER.METHOD_NOT_IMPLEMENTED", decodeJWTPayloadError.getMessage());

        UnsupportedOperationException verifyTokenError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> authenticationTokenManager.verifyToken(null)
        );
        Assertions.assertEquals("AUTHENTICATION_TOKEN_MANAGER.METHOD_NOT_IMPLEMENTED", verifyTokenError.getMessage());
    }
}
