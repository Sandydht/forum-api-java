package forum.api.java.applications.usecase;

import forum.api.java.applications.security.AuthenticationTokenManager;
import forum.api.java.domain.authentication.AuthenticationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@DisplayName("Refresh authentication use case")
@ExtendWith(MockitoExtension.class)
public class RefreshAuthenticationUseCaseTest {
    @Mock
    private AuthenticationRepository authenticationRepository;

    @Mock
    private AuthenticationTokenManager authenticationTokenManager;

    @InjectMocks
    private RefreshAuthenticationUseCase refreshAuthenticationUseCase;

    @Test
    @DisplayName("should orchestrating the refresh authentication action correctly")
    public void shouldOrchestratingTheRefreshAuthenticationActionCorrectly() {
        String refreshToken = "refresh-token";
        String accessToken = "access-token";
        String userId = UUID.randomUUID().toString();

        Mockito.doNothing().when(authenticationTokenManager).verifyToken(refreshToken);
        Mockito.doNothing().when(authenticationRepository).checkAvailabilityToken(refreshToken);
        Mockito.when(authenticationTokenManager.decodeJWTPayload(refreshToken)).thenReturn(userId);
        Mockito.when(authenticationTokenManager.createAccessToken(userId)).thenReturn(accessToken);

        String newAccessToken = refreshAuthenticationUseCase.execute(refreshToken);

        Assertions.assertEquals(accessToken, newAccessToken);

        Mockito.verify(authenticationTokenManager, Mockito.times(1)).verifyToken(refreshToken);
        Mockito.verify(authenticationRepository, Mockito.times(1)).checkAvailabilityToken(refreshToken);
        Mockito.verify(authenticationTokenManager, Mockito.times(1)).decodeJWTPayload(refreshToken);
        Mockito.verify(authenticationTokenManager, Mockito.times(1)).createAccessToken(userId);
    }
}
