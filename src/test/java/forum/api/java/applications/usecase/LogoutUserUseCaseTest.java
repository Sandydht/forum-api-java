package forum.api.java.applications.usecase;

import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.authentication.entity.LogoutAuth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Logout user use case")
@ExtendWith(MockitoExtension.class)
public class LogoutUserUseCaseTest {
    @Mock
    private AuthenticationRepository authenticationRepository;

    @InjectMocks
    private LogoutUserUseCase logoutUserUseCase;

    @Test
    @DisplayName("should orchestrating the logout user action correctly")
    public void testLogoutUserActionCorrectly() {
        String refreshToken = "refresh-token";

        LogoutAuth logoutAuth = new LogoutAuth(refreshToken);
        Mockito.doNothing().when(authenticationRepository).checkAvailabilityToken(logoutAuth.getRefreshToken());
        Mockito.doNothing().when(authenticationRepository).deleteToken(logoutAuth.getRefreshToken());

        logoutUserUseCase.execute(logoutAuth);

        Mockito.verify(authenticationRepository, Mockito.times(1)).checkAvailabilityToken(logoutAuth.getRefreshToken());
        Mockito.verify(authenticationRepository, Mockito.times(1)).deleteToken(logoutAuth.getRefreshToken());
    }
}
