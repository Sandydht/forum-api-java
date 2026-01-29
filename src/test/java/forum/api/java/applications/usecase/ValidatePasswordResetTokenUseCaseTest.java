package forum.api.java.applications.usecase;

import forum.api.java.applications.security.PasswordHash;
import forum.api.java.domain.authentication.AuthenticationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@DisplayName("ValidatePasswordResetTokenUseCase")
@ExtendWith(MockitoExtension.class)
public class ValidatePasswordResetTokenUseCaseTest {
    @Mock
    private PasswordHash passwordHash;

    @Mock
    private AuthenticationRepository authenticationRepository;

    @InjectMocks
    private ValidatePasswordResetTokenUseCase validatePasswordResetTokenUseCase;

    @Test
    @DisplayName("should orchestrating validate password reset token action correctly")
    public void shouldOrchestratingPasswordResetTokenActionCorrectly() throws NoSuchAlgorithmException, InvalidKeyException {
        String rawToken = "raw-token";
        String tokenHash = "token-hash";

        Mockito.when(passwordHash.hashToken(rawToken)).thenReturn(tokenHash);
        Mockito.doNothing().when(authenticationRepository).validatePasswordResetToken(tokenHash);

        validatePasswordResetTokenUseCase.execute(rawToken);

        Mockito.verify(passwordHash, Mockito.times(1)).hashToken(rawToken);
        Mockito.verify(authenticationRepository, Mockito.times(1)).validatePasswordResetToken(tokenHash);
    }
}
