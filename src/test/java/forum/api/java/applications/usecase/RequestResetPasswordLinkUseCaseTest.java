package forum.api.java.applications.usecase;

import forum.api.java.applications.security.CaptchaService;
import forum.api.java.applications.security.PasswordHash;
import forum.api.java.applications.service.EmailService;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.authentication.entity.AddedPasswordResetToken;
import forum.api.java.domain.authentication.entity.RequestResetPasswordLink;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.UserDetail;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@DisplayName("Request reset password link use case")
@ExtendWith(MockitoExtension.class)
public class RequestResetPasswordLinkUseCaseTest {
    @Mock
    private CaptchaService captchaService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private AuthenticationRepository authenticationRepository;

    @Mock
    private PasswordHash passwordHash;

    @InjectMocks
    private RequestResetPasswordLinkUseCase requestResetPasswordLinkUseCase;

    @Test
    @DisplayName("should orchestrating the request reset password link action correctly")
    public void shouldOrchestratingTheRequestResetPasswordLinkActionCorrectly() throws MessagingException {
        String id = UUID.randomUUID().toString();
        Instant expiresAt = Instant.now();
        Optional<Instant> usedAt = Optional.empty();
        String ipRequest = "192.168.1.1";
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();
        Optional<Instant> deletedAt = Optional.empty();

        String tokenHash = "hashed-token";

        String userId = UUID.randomUUID().toString();
        String username = "user";
        String email = "example@email.com";
        String phoneNumber = "+6281123123123";
        String fullname = "Fullname";
        String password = "password";

        String captchaToken = "captcha-token";
        String emailLink = "http://localhost:5173/forgot-password?token=" + tokenHash;

        RequestResetPasswordLink requestResetPasswordLink = new RequestResetPasswordLink(email, ipRequest, userAgent, captchaToken);

        Mockito.doNothing().when(captchaService).verifyToken(requestResetPasswordLink.getCaptchaToken());
        Mockito.when(userRepository.getUserByEmail(requestResetPasswordLink.getEmail())).thenReturn(new UserDetail(userId, username, email, phoneNumber, fullname, password));
        Mockito.when(passwordHash.hashPassword(Mockito.anyString())).thenReturn(tokenHash);
        Mockito.when(authenticationRepository.addPasswordResetToken(userId, tokenHash, requestResetPasswordLink.getIpRequest(), requestResetPasswordLink.getUserAgent())).thenReturn(new AddedPasswordResetToken(
                id,
                userId,
                tokenHash,
                expiresAt,
                usedAt,
                ipRequest,
                userAgent,
                createdAt,
                updatedAt,
                deletedAt
        ));
        Mockito.doNothing().when(emailService).sendVerificationEmail(email, fullname, emailLink);

        AddedPasswordResetToken result = requestResetPasswordLinkUseCase.execute(requestResetPasswordLink);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(userId, result.getUserId());
        Assertions.assertEquals(tokenHash, result.getTokenHash());
        Assertions.assertEquals(expiresAt, result.getExpiresAt());
        Assertions.assertEquals(usedAt, result.getUsedAt());
        Assertions.assertEquals(ipRequest, result.getIpRequest());
        Assertions.assertEquals(userAgent, result.getUserAgent());
        Assertions.assertEquals(createdAt, result.getCreatedAt());
        Assertions.assertEquals(updatedAt, result.getUpdatedAt());
        Assertions.assertEquals(deletedAt, result.getDeletedAt());

        Mockito.verify(captchaService, Mockito.times(1)).verifyToken(requestResetPasswordLink.getCaptchaToken());
        Mockito.verify(userRepository, Mockito.times(1)).getUserByEmail(requestResetPasswordLink.getEmail());
        Mockito.verify(passwordHash, Mockito.times(1)).hashPassword(Mockito.anyString());
        Mockito.verify(authenticationRepository, Mockito.times(1)).addPasswordResetToken(userId, tokenHash, requestResetPasswordLink.getIpRequest(), requestResetPasswordLink.getUserAgent());
        Mockito.verify(emailService, Mockito.times(1)).sendVerificationEmail(email, fullname, emailLink);
    }
}
