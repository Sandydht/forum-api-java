package forum.api.java.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@DisplayName("AuthenticationTokenManagerImpl")
@ExtendWith(MockitoExtension.class)
public class AuthenticationTokenManagerImplTest {
    private MockedStatic<JWT> mockedJWT;
    private final JWTCreator.Builder jwtMockBuilder = Mockito.mock(JWTCreator.Builder.class);
    private AuthenticationTokenManagerImpl authenticationTokenManagerImpl;

    @BeforeEach
    public void setUp() {
        mockedJWT = Mockito.mockStatic(JWT.class);
        authenticationTokenManagerImpl = new AuthenticationTokenManagerImpl(new JWT());
    }

    @AfterEach
    public void tearDown() {
        if (mockedJWT != null) {
            mockedJWT.close();
        }
    }

    @Test
    @DisplayName("should create accessToken correctly")
    public void testCreateAccessTokenCorrectly() {
        UUID id = UUID.randomUUID();
        String fakeToken = "fake-token";

        mockedJWT.when(JWT::create).thenReturn(jwtMockBuilder);
        Mockito.when(jwtMockBuilder.withSubject(id.toString())).thenReturn(jwtMockBuilder);
        Mockito.when(jwtMockBuilder.withExpiresAt(any(Date.class))).thenReturn(jwtMockBuilder);
        Mockito.when(jwtMockBuilder.sign(any(Algorithm.class))).thenReturn(fakeToken);

        String accessToken = authenticationTokenManagerImpl.createAccessToken(id);

        Assertions.assertNotNull(accessToken);
        Assertions.assertEquals(fakeToken, accessToken);

        mockedJWT.verify(JWT::create, Mockito.times(1));
        Mockito.verify(jwtMockBuilder, Mockito.times(1)).withSubject(id.toString());
        Mockito.verify(jwtMockBuilder, Mockito.times(1)).withExpiresAt(any(Date.class));
        Mockito.verify(jwtMockBuilder, Mockito.times(1)).sign(any(Algorithm.class));
    }

    @Test
    @DisplayName("should create refreshToken correctly")
    public void testCreateRefreshTokenCorrectly() {
        UUID id = UUID.randomUUID();
        String fakeToken = "fake-token";

        mockedJWT.when(JWT::create).thenReturn(jwtMockBuilder);
        Mockito.when(jwtMockBuilder.withSubject(id.toString())).thenReturn(jwtMockBuilder);
        Mockito.when(jwtMockBuilder.withExpiresAt(any(Date.class))).thenReturn(jwtMockBuilder);
        Mockito.when(jwtMockBuilder.sign(any(Algorithm.class))).thenReturn(fakeToken);

        String refreshToken = authenticationTokenManagerImpl.createRefreshToken(id);

        Assertions.assertNotNull(refreshToken);
        Assertions.assertEquals(fakeToken, refreshToken);

        mockedJWT.verify(JWT::create, Mockito.times(1));
        Mockito.verify(jwtMockBuilder, Mockito.times(1)).withSubject(id.toString());
        Mockito.verify(jwtMockBuilder, Mockito.times(1)).withExpiresAt(any(Date.class));
        Mockito.verify(jwtMockBuilder, Mockito.times(1)).sign(any(Algorithm.class));
    }

    @Test
    @DisplayName("should decode access token payload correctly")
    public void testDecodeAccessTokenPayloadCorrectly() {
        UUID id = UUID.randomUUID();
        String fakeToken = "fake-token";

        mockedJWT.when(JWT::create).thenReturn(jwtMockBuilder);
        Mockito.when(jwtMockBuilder.withSubject(id.toString())).thenReturn(jwtMockBuilder);
        Mockito.when(jwtMockBuilder.withExpiresAt(any(Date.class))).thenReturn(jwtMockBuilder);
        Mockito.when(jwtMockBuilder.sign(any(Algorithm.class))).thenReturn(fakeToken);

        String accessToken = authenticationTokenManagerImpl.createAccessToken(id);

        DecodedJWT mockDecodedJWT = Mockito.mock(DecodedJWT.class);
        Mockito.when(mockDecodedJWT.getSubject()).thenReturn(id.toString());

        mockedJWT.when(() -> JWT.decode(accessToken)).thenReturn(mockDecodedJWT);

        Assertions.assertNotNull(JWT.decode(accessToken).getSubject());
        Assertions.assertEquals(id.toString(), JWT.decode(accessToken).getSubject());

        mockedJWT.verify(JWT::create, Mockito.times(1));
        Mockito.verify(jwtMockBuilder, Mockito.times(1)).withSubject(id.toString());
        Mockito.verify(jwtMockBuilder, Mockito.times(1)).withExpiresAt(any(Date.class));
        Mockito.verify(jwtMockBuilder, Mockito.times(1)).sign(any(Algorithm.class));
        mockedJWT.verify(() -> JWT.decode(accessToken), Mockito.times(2));
    }

    @Test
    @DisplayName("should decode refresh token payload correctly")
    public void testDecodeRefreshTokenPayloadCorrectly() {
        UUID id = UUID.randomUUID();
        String fakeToken = "fake-token";

        mockedJWT.when(JWT::create).thenReturn(jwtMockBuilder);
        Mockito.when(jwtMockBuilder.withSubject(id.toString())).thenReturn(jwtMockBuilder);
        Mockito.when(jwtMockBuilder.withExpiresAt(any(Date.class))).thenReturn(jwtMockBuilder);
        Mockito.when(jwtMockBuilder.sign(any(Algorithm.class))).thenReturn(fakeToken);

        String refreshToken = authenticationTokenManagerImpl.createRefreshToken(id);

        DecodedJWT mockDecodedJWT = Mockito.mock(DecodedJWT.class);
        Mockito.when(mockDecodedJWT.getSubject()).thenReturn(id.toString());

        mockedJWT.when(() -> JWT.decode(refreshToken)).thenReturn(mockDecodedJWT);

        Assertions.assertNotNull(JWT.decode(refreshToken).getSubject());
        Assertions.assertEquals(id.toString(), JWT.decode(refreshToken).getSubject());

        mockedJWT.verify(JWT::create, Mockito.times(1));
        Mockito.verify(jwtMockBuilder, Mockito.times(1)).withSubject(id.toString());
        Mockito.verify(jwtMockBuilder, Mockito.times(1)).withExpiresAt(any(Date.class));
        Mockito.verify(jwtMockBuilder, Mockito.times(1)).sign(any(Algorithm.class));
        mockedJWT.verify(() -> JWT.decode(refreshToken), Mockito.times(2));
    }
}
