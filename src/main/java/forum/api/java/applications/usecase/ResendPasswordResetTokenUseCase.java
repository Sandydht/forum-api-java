package forum.api.java.applications.usecase;

import forum.api.java.applications.security.PasswordHash;
import forum.api.java.applications.service.EmailService;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.authentication.entity.ResendPasswordResetToken;
import jakarta.mail.MessagingException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class ResendPasswordResetTokenUseCase {
    private final PasswordHash passwordHash;
    private final AuthenticationRepository authenticationRepository;
    private final EmailService emailService;

    public ResendPasswordResetTokenUseCase(PasswordHash passwordHash, AuthenticationRepository authenticationRepository, EmailService emailService) {
        this.passwordHash = passwordHash;
        this.authenticationRepository = authenticationRepository;
        this.emailService = emailService;
    }

    public void execute(ResendPasswordResetToken resendPasswordResetToken) throws NoSuchAlgorithmException, InvalidKeyException {
        String tokenHash = this.passwordHash.hashToken(resendPasswordResetToken.getRawToken());

        this.authenticationRepository
                .findPasswordResetTokenByTokenHash(tokenHash)
                .ifPresent(oldToken -> {
                    try {
                        if (oldToken.getUser() == null) return;
                        this.authenticationRepository.invalidatePasswordResetToken(oldToken);

                        SecureRandom random = new SecureRandom();
                        byte[] bytes = new byte[32]; // 32 bytes = 256 bit
                        random.nextBytes(bytes);
                        String newRawToken = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

                        String newTokenHash = passwordHash.hashToken(newRawToken);

                        authenticationRepository.addPasswordResetToken(
                                oldToken.getUser().getEmail(),
                                newTokenHash,
                                resendPasswordResetToken.getIpRequest(),
                                resendPasswordResetToken.getUserAgent()
                        );

                        String emailLink = "http://localhost:5173/forgot-password?token=" + newRawToken;
                        emailService.sendForgotPasswordEmail(
                                oldToken.getUser().getEmail(),
                                oldToken.getUser().getFullname(),
                                emailLink
                        );
                    } catch (NoSuchAlgorithmException | InvalidKeyException | MessagingException error) {
                        throw new RuntimeException(error);
                    }
                });
    }
}
