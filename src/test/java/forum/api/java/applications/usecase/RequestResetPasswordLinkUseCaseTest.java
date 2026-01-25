package forum.api.java.applications.usecase;

import forum.api.java.applications.security.CaptchaService;
import forum.api.java.applications.security.PasswordHash;
import forum.api.java.applications.service.EmailService;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.authentication.entity.RequestResetPasswordLink;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.UserDetail;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@DisplayName("Request reset password link use case")
@ExtendWith(MockitoExtension.class)
public class RequestResetPasswordLinkUseCaseTest {
    @Mock
    private CaptchaService captchaService;

    @Mock
    private EmailService emailService;

    @Mock
    private AuthenticationRepository authenticationRepository;

    @Mock
    private PasswordHash passwordHash;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RequestResetPasswordLinkUseCase requestResetPasswordLinkUseCase;

    @Test
    @DisplayName("should orchestrating the request reset password link action correctly")
    public void shouldOrchestratingTheRequestResetPasswordLinkActionCorrectly() throws MessagingException {
        String ipRequest = "192.168.1.1";
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";

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
        Mockito.when(userRepository.getUserByEmailForgotPassword(requestResetPasswordLink.getEmail())).thenReturn(Optional.of(new UserDetail(userId, username, email, phoneNumber, fullname, password)));
        Mockito.when(passwordHash.hashPassword(Mockito.anyString())).thenReturn(tokenHash);
        Mockito.doNothing().when(authenticationRepository).addPasswordResetToken(email, tokenHash, requestResetPasswordLink.getIpRequest(), requestResetPasswordLink.getUserAgent());
        Mockito.doNothing().when(emailService).sendForgotPasswordEmail(email, fullname, emailLink);

        requestResetPasswordLinkUseCase.execute(requestResetPasswordLink);

        Mockito.verify(captchaService, Mockito.times(1)).verifyToken(requestResetPasswordLink.getCaptchaToken());
        Mockito.verify(userRepository, Mockito.times(1)).getUserByEmailForgotPassword(requestResetPasswordLink.getEmail());
        Mockito.verify(passwordHash, Mockito.times(1)).hashPassword(Mockito.anyString());
        Mockito.verify(authenticationRepository, Mockito.times(1)).addPasswordResetToken(email, tokenHash, requestResetPasswordLink.getIpRequest(), requestResetPasswordLink.getUserAgent());
        Mockito.verify(emailService, Mockito.times(1)).sendForgotPasswordEmail(email, fullname, emailLink);
    }
}
