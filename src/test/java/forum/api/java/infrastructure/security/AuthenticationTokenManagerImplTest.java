package forum.api.java.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.*;

import java.util.UUID;

@DisplayName("AuthenticationTokenManagerImpl")
public class AuthenticationTokenManagerImplTest {
    private final AuthenticationTokenManagerImpl authenticationTokenManagerImpl = new AuthenticationTokenManagerImpl();

    @Test
    @DisplayName("should create accessToken correctly")
    public void testCreateAccessTokenCorrectly() {
        String id = UUID.randomUUID().toString();

        String accessToken = authenticationTokenManagerImpl.createAccessToken(id);
        DecodedJWT decodeAccessToken = JWT.decode(accessToken);

        Assertions.assertNotNull(accessToken);
        Assertions.assertEquals(id, decodeAccessToken.getSubject());
    }

    @Test
    @DisplayName("should create refreshToken correctly")
    public void testCreateRefreshTokenCorrectly() {
        String id = UUID.randomUUID().toString();

        String refreshToken = authenticationTokenManagerImpl.createRefreshToken(id);
        DecodedJWT decodeRefreshToken = JWT.decode(refreshToken);

        Assertions.assertNotNull(refreshToken);
        Assertions.assertEquals(id, decodeRefreshToken.getSubject());
    }

    @Test
    @DisplayName("should decode token correctly")
    public void testDecodeTokeCorrectly() {
        String id = UUID.randomUUID().toString();

        String accessToken = authenticationTokenManagerImpl.createAccessToken(id);
        DecodedJWT decodeAccessToken = JWT.decode(accessToken);
        Assertions.assertEquals(id, decodeAccessToken.getSubject());

        String refreshToken = authenticationTokenManagerImpl.createRefreshToken(id);
        DecodedJWT decodeRefreshToken = JWT.decode(refreshToken);
        Assertions.assertEquals(id, decodeRefreshToken.getSubject());
    }
}
