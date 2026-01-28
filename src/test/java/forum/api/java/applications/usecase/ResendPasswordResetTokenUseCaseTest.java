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

@DisplayName("Resend password reset token use case")
@ExtendWith(MockitoExtension.class)
public class ResendPasswordResetTokenUseCaseTest {
    @Mock
    private PasswordHash passwordHash;

    @Mock
    private AuthenticationRepository authenticationRepository;

    @InjectMocks
    private ResendPasswordResetTokenUseCase resendPasswordResetTokenUseCase;

    @Test
    @DisplayName("should orchestrating the resend password reset token action correctly")
    public void shouldOrchestratingTheResendPasswordResetTokenActionCorrectly() throws NoSuchAlgorithmException, InvalidKeyException {
        String rawToken = "raw-token";
        String tokenHash = "hashed-token";
        String ipRequest = "192.168.1.1";
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";

        Mockito.when(passwordHash.hashToken(rawToken)).thenReturn(tokenHash);
    }
}
