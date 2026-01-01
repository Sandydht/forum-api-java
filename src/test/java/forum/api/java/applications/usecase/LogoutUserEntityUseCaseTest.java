package forum.api.java.applications.usecase;

import forum.api.java.domain.authentication.AuthenticationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Logout user use case")
@ExtendWith(MockitoExtension.class)
public class LogoutUserEntityUseCaseTest {
    @Mock
    private AuthenticationRepository authenticationRepository;

    @InjectMocks
    private LogoutUserUseCase logoutUserUseCase;

    @Test
    @DisplayName("should orchestrating the logout user action correctly")
    public void testLogoutUserActionCorrectly() {
        String refreshToken = "refresh-token";

        Mockito.doNothing().when(authenticationRepository).checkAvailabilityToken(refreshToken);
        Mockito.doNothing().when(authenticationRepository).deleteToken(refreshToken);

        logoutUserUseCase.execute(refreshToken);

        Mockito.verify(authenticationRepository, Mockito.times(1)).checkAvailabilityToken(refreshToken);
        Mockito.verify(authenticationRepository, Mockito.times(1)).deleteToken(refreshToken);
    }
}
