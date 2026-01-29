package forum.api.java.applications.usecase;

import forum.api.java.applications.security.PasswordHash;
import forum.api.java.applications.service.EmailService;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.authentication.entity.PasswordResetTokenDetail;
import forum.api.java.domain.authentication.entity.ResendPasswordResetToken;
import forum.api.java.domain.user.entity.UserDetail;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Optional;

@DisplayName("Resend password reset token use case")
@ExtendWith(MockitoExtension.class)
public class ResendPasswordResetTokenUseCaseTest {
    @Mock
    private PasswordHash passwordHash;

    @Mock
    private AuthenticationRepository authenticationRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ResendPasswordResetTokenUseCase resendPasswordResetTokenUseCase;

    @Test
    @DisplayName("should orchestrating the resend password reset token action correctly")
    public void shouldOrchestratingTheResendPasswordResetTokenActionCorrectly() throws NoSuchAlgorithmException, InvalidKeyException, MessagingException {
        String rawToken = "raw-token";
        String tokenHash = "hashed-token";
        String newTokenHash = "new-hashed-token";

        String userId = "user-123";
        String username = "user";
        String email = "example@email.com";
        String phoneNumber = "081123123123";
        String fullname = "Fullname";
        String password = "password";

        String id = "password-reset-token-123";
        UserDetail user = new UserDetail(userId, username, email, phoneNumber, fullname, password);
        Instant expiresAt = Instant.now().plusSeconds(3600);
        Optional<Instant> usedAt = Optional.empty();
        String ipRequest = "192.168.1.1";
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();
        Optional<Instant> deletedAt = Optional.empty();

        ResendPasswordResetToken resendPasswordResetToken = new ResendPasswordResetToken(rawToken, ipRequest, userAgent);
        PasswordResetTokenDetail passwordResetTokenDetail = new PasswordResetTokenDetail(id, user, tokenHash, expiresAt, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt);

        Mockito.when(passwordHash.hashToken(rawToken)).thenReturn(tokenHash);
        Mockito.when(authenticationRepository.findPasswordResetTokenByTokenHash(tokenHash)).thenReturn(Optional.of(passwordResetTokenDetail));
        Mockito.doNothing().when(authenticationRepository).invalidatePasswordResetToken(passwordResetTokenDetail);
        Mockito.when(passwordHash.hashToken(Mockito.argThat(t -> !t.equals(rawToken)))).thenReturn(newTokenHash);
        Mockito.doNothing().when(authenticationRepository).addPasswordResetToken(
                passwordResetTokenDetail.getUser().getEmail(),
                newTokenHash,
                passwordResetTokenDetail.getIpRequest(),
                passwordResetTokenDetail.getUserAgent()
        );
        Mockito.doNothing().when(emailService).sendForgotPasswordEmail(
                Mockito.eq(passwordResetTokenDetail.getUser().getEmail()),
                Mockito.eq(passwordResetTokenDetail.getUser().getFullname()),
                Mockito.argThat(link -> link.startsWith("http://localhost:5173/forgot-password?token="))
        );

        resendPasswordResetTokenUseCase.execute(resendPasswordResetToken);

        Mockito.verify(passwordHash, Mockito.times(2)).hashToken(Mockito.anyString());
        Mockito.verify(authenticationRepository, Mockito.times(1)).findPasswordResetTokenByTokenHash(tokenHash);
        Mockito.verify(authenticationRepository, Mockito.times(1)).invalidatePasswordResetToken(passwordResetTokenDetail);
        Mockito.verify(authenticationRepository, Mockito.times(1)).addPasswordResetToken(
                passwordResetTokenDetail.getUser().getEmail(),
                newTokenHash,
                passwordResetTokenDetail.getIpRequest(),
                passwordResetTokenDetail.getUserAgent()
        );
        Mockito.verify(emailService, Mockito.times(1)).sendForgotPasswordEmail(
                Mockito.eq(passwordResetTokenDetail.getUser().getEmail()),
                Mockito.eq(passwordResetTokenDetail.getUser().getFullname()),
                Mockito.argThat(link -> link.startsWith("http://localhost:5173/forgot-password?token="))
        );
    }
}
