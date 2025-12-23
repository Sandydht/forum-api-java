package forum.api.java.applications.usecase;

import forum.api.java.domain.authentication.AuthenticationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

@DisplayName("Clean up expired token use case")
@ExtendWith(MockitoExtension.class)
public class CleanupExpiredTokenUseCaseTest {
    @Mock
    private AuthenticationRepository authenticationRepository;

    @InjectMocks
    private CleanupExpiredTokenUseCase cleanupExpiredTokenUseCase;

    @Test
    @DisplayName("should orchestrating the delete expired tokens action correctly")
    public void testDeleteExpiredTokensActionCorrectly() {
        Instant now = Instant.now();

        Mockito.doNothing().when(authenticationRepository).deleteExpiredTokens(now);

        cleanupExpiredTokenUseCase.execute();


    }
}
