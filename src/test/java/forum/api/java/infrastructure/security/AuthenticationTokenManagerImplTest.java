package forum.api.java.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import forum.api.java.infrastructure.properties.JwtProperties;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@DisplayName("AuthenticationTokenManagerImpl")
public class AuthenticationTokenManagerImplTest {
    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private AuthenticationTokenManagerImpl authenticationTokenManagerImpl;

    @Test
    @DisplayName("should create accessToken correctly")
    public void testCreateAccessTokenCorrectly() {
        System.out.println("secret key: " + jwtProperties.getSecretKey());
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
