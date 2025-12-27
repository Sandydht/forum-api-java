package forum.api.java.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.*;

import java.util.UUID;

@DisplayName("AuthenticationTokenManagerImpl")
public class AuthenticationTokenManagerImplTest {
    private final Algorithm algorithm = Algorithm.HMAC256("secret-key-test");
    private final AuthenticationTokenManagerImpl authenticationTokenManagerImpl = new AuthenticationTokenManagerImpl(algorithm);

    @Nested
    @DisplayName("createAccessToken function")
    public class CreateAccessToken {
        @Test
        @DisplayName("should create accessToken correctly")
        public void testCreateAccessTokenCorrectly() {
            String id = UUID.randomUUID().toString();

            String accessToken = authenticationTokenManagerImpl.createAccessToken(id);
            DecodedJWT decodeAccessToken = JWT.decode(accessToken);

            Assertions.assertNotNull(accessToken);
            Assertions.assertEquals(id, decodeAccessToken.getSubject());
        }
    }

    @Nested
    @DisplayName("createRefreshToken function")
    public class CreateRefreshToken {
        @Test
        @DisplayName("should create refreshToken correctly")
        public void testCreateRefreshTokenCorrectly() {
            String id = UUID.randomUUID().toString();

            String refreshToken = authenticationTokenManagerImpl.createRefreshToken(id);
            DecodedJWT decodeRefreshToken = JWT.decode(refreshToken);

            Assertions.assertNotNull(refreshToken);
            Assertions.assertEquals(id, decodeRefreshToken.getSubject());
        }
    }

    @Nested
    @DisplayName("decodeJWTPayload")
    public class DecodeJWTPayload {
        @Test
        @DisplayName("should decode token correctly")
        public void testDecodeTokeCorrectly() {
            String id = UUID.randomUUID().toString();

            String accessToken = authenticationTokenManagerImpl.createAccessToken(id);
            String decodeAccessToken = authenticationTokenManagerImpl.decodeJWTPayload(accessToken);
            Assertions.assertEquals(id, decodeAccessToken);

            String refreshToken = authenticationTokenManagerImpl.createRefreshToken(id);
            String decodeRefreshToken = authenticationTokenManagerImpl.decodeJWTPayload(refreshToken);
            Assertions.assertEquals(id, decodeRefreshToken);
        }
    }
}
