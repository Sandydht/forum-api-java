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

import java.security.SecureRandom;
import java.util.Base64;

public class RequestResetPasswordLinkUseCase {
    private final CaptchaService captchaService;
    private final UserRepository userRepository;
    private final PasswordHash passwordHash;
    private final AuthenticationRepository authenticationRepository;
    private final EmailService emailService;

    public RequestResetPasswordLinkUseCase(
            CaptchaService captchaService,
            UserRepository userRepository,
            PasswordHash passwordHash,
            AuthenticationRepository authenticationRepository,
            EmailService emailService
    ) {
        this.captchaService = captchaService;
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
        this.authenticationRepository = authenticationRepository;
        this.emailService = emailService;
    }

    public AddedPasswordResetToken execute(RequestResetPasswordLink requestResetPasswordLink) throws MessagingException {
        captchaService.verifyToken(requestResetPasswordLink.getCaptchaToken());
        UserDetail userDetail = userRepository.getUserByEmail(requestResetPasswordLink.getEmail());

        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32]; // 32 bytes = 256 bit
        random.nextBytes(bytes);
        String rawToken = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        String tokenHash = passwordHash.hashPassword(rawToken);

        AddedPasswordResetToken result = authenticationRepository.addPasswordResetToken(
                userDetail.getId(),
                tokenHash,
                requestResetPasswordLink.getIpRequest(),
                requestResetPasswordLink.getUserAgent()
        );

        String emailLink = "http://localhost:5173/forgot-password?token=" + tokenHash;
        emailService.sendForgotPasswordEmail(userDetail.getEmail(), userDetail.getFullname(), emailLink);

        return result;
    }
}
