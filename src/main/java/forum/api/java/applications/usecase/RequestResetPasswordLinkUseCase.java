package forum.api.java.applications.usecase;

import forum.api.java.applications.security.CaptchaService;
import forum.api.java.applications.security.PasswordHash;
import forum.api.java.applications.service.EmailService;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.authentication.entity.RequestResetPasswordLink;
import forum.api.java.domain.user.UserRepository;
import jakarta.mail.MessagingException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class RequestResetPasswordLinkUseCase {
    private final CaptchaService captchaService;
    private final PasswordHash passwordHash;
    private final AuthenticationRepository authenticationRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public RequestResetPasswordLinkUseCase(
            CaptchaService captchaService,
            PasswordHash passwordHash,
            AuthenticationRepository authenticationRepository,
            UserRepository userRepository,
            EmailService emailService
    ) {
        this.captchaService = captchaService;
        this.passwordHash = passwordHash;
        this.authenticationRepository = authenticationRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public void execute(RequestResetPasswordLink requestResetPasswordLink) {
        captchaService.verifyToken(requestResetPasswordLink.getCaptchaToken());

        userRepository.getUserByEmailForgotPassword(requestResetPasswordLink.getEmail())
                .ifPresent(user -> {
                    try {
                        SecureRandom random = new SecureRandom();
                        byte[] bytes = new byte[32]; // 32 bytes = 256 bit
                        random.nextBytes(bytes);
                        String rawToken = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

                        String tokenHash = passwordHash.hashToken(rawToken);

                        authenticationRepository.addPasswordResetToken(
                                user.getEmail(),
                                tokenHash,
                                requestResetPasswordLink.getIpRequest(),
                                requestResetPasswordLink.getUserAgent()
                        );

                        String emailLink = "http://localhost:5173/forgot-password?token=" + rawToken;
                        emailService.sendForgotPasswordEmail(user.getEmail(), user.getFullname(), emailLink);
                    } catch (MessagingException | NoSuchAlgorithmException | InvalidKeyException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
