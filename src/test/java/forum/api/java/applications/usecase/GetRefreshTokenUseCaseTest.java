package forum.api.java.applications.usecase;

import forum.api.java.domain.authentication.AuthenticationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Get refresh token use case")
@ExtendWith(MockitoExtension.class)
public class GetRefreshTokenUseCaseTest {
    @Mock
    private AuthenticationRepository authenticationRepository;

    @InjectMocks
    private GetRefreshTokenUseCase getRefreshTokenUseCase;

    @Test
    @DisplayName("should orchestrating the get refresh token use case action correctly")
    public void shouldOrchestratingTheGetRefreshTokenUseCaseActionCorrectly() {
        String username = "user";
        String refreshToken = "refresh-token";

        Mockito.when(authenticationRepository.getTokenByUsername(username)).thenReturn(refreshToken);

        String savedRefreshToken = getRefreshTokenUseCase.execute(username);

        Assertions.assertEquals(refreshToken, savedRefreshToken);

        Mockito.verify(authenticationRepository, Mockito.times(1)).getTokenByUsername(username);
    }
}
