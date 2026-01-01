package forum.api.java.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import forum.api.java.commons.exceptions.ClientException;
import forum.api.java.commons.exceptions.InvariantException;
import org.junit.jupiter.api.*;

import java.util.UUID;

@DisplayName("AuthenticationTokenManagerImpl")
public class AuthenticationTokenManagerImplTest {
    private final Algorithm algorithm = Algorithm.HMAC256("secret-key-test");
    private final AuthenticationTokenManagerImpl authenticationTokenManagerImpl = new AuthenticationTokenManagerImpl(algorithm);

    @Nested
    @DisplayName("createAccessToken function")
    public class CreateAccessTokenFunction {
        @Test
        @DisplayName("should create accessToken correctly")
        public void shouldCreateAccessTokenCorrectly() {
            String id = UUID.randomUUID().toString();

            String accessToken = authenticationTokenManagerImpl.createAccessToken(id);
            DecodedJWT decodeAccessToken = JWT.decode(accessToken);

            Assertions.assertNotNull(accessToken);
            Assertions.assertEquals(id, decodeAccessToken.getSubject());
        }
    }

    @Nested
    @DisplayName("createRefreshToken function")
    public class CreateRefreshTokenEntityFunction {
        @Test
        @DisplayName("should create refreshToken correctly")
        public void shouldCreateRefreshTokenCorrectly() {
            String id = UUID.randomUUID().toString();

            String refreshToken = authenticationTokenManagerImpl.createRefreshToken(id);
            DecodedJWT decodeRefreshToken = JWT.decode(refreshToken);

            Assertions.assertNotNull(refreshToken);
            Assertions.assertEquals(id, decodeRefreshToken.getSubject());
        }
    }

    @Nested
    @DisplayName("decodeJWTPayload function")
    public class DecodeJWTPayloadFunction {
        @Test
        @DisplayName("should decode token correctly")
        public void shouldDecodeTokenCorrectly() {
            String id = UUID.randomUUID().toString();

            String accessToken = authenticationTokenManagerImpl.createAccessToken(id);
            String decodeAccessToken = authenticationTokenManagerImpl.decodeJWTPayload(accessToken);
            Assertions.assertEquals(id, decodeAccessToken);

            String refreshToken = authenticationTokenManagerImpl.createRefreshToken(id);
            String decodeRefreshToken = authenticationTokenManagerImpl.decodeJWTPayload(refreshToken);
            Assertions.assertEquals(id, decodeRefreshToken);
        }
    }

    @Nested
    @DisplayName("verifyToken function")
    public class verifyTokenFunction {
        @Test
        @DisplayName("should throw InvariantException when verification failed")
        public void shouldThrowInvariantExceptionWhenVerificationFailed() {
            String invalidToken = "invalid.token";

            InvariantException exception = Assertions.assertThrows(
                    InvariantException.class,
                    () -> authenticationTokenManagerImpl.verifyToken(invalidToken)
            );

            Assertions.assertEquals("VERIFICATION_FAILED", exception.getMessage());
        }

        @Test
        @DisplayName("should throw InvariantException when token is expired")
        public void shouldThrowInvariantExceptionWhenTokenIsExpired() {
            String expiredToken = JWT.create()
                    .withExpiresAt(new java.util.Date(System.currentTimeMillis() - 1000))
                    .sign(algorithm);

            Assertions.assertThrows(
                    InvariantException.class,
                    () -> authenticationTokenManagerImpl.verifyToken(expiredToken)
            );
        }

        @Test
        @DisplayName("should not throw InvariantException when token is valid")
        public void shouldNotThrowInvariantExceptionWhenTokenIsValid() {
            String userId = UUID.randomUUID().toString();
            String validToken = JWT.create()
                    .withSubject(userId)
                    .withExpiresAt(new java.util.Date(System.currentTimeMillis() + 60000))
                    .sign(algorithm);

            Assertions.assertDoesNotThrow(() -> authenticationTokenManagerImpl.verifyToken(validToken));
        }
    }
}
