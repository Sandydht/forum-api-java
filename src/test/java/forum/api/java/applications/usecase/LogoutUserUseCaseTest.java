package forum.api.java.applications.usecase;

import forum.api.java.domain.authentication.AuthenticationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@DisplayName("Logout user use case")
@ExtendWith(MockitoExtension.class)
public class LogoutUserUseCaseTest {
    @Mock
    private AuthenticationRepository authenticationRepository;

    @InjectMocks
    private LogoutUserUseCase logoutUserUseCase;

    @Test
    @DisplayName("should orchestrating the logout user action correctly")
    public void shouldOrchestratingTheLogoutUserActionCorrectly() {
        String userId = UUID.randomUUID().toString();

        Mockito.doNothing().when(authenticationRepository).deleteTokenByUserId(userId);

        logoutUserUseCase.execute(userId);

        Mockito.verify(authenticationRepository, Mockito.times(1)).deleteTokenByUserId(userId);
    }
}
