package forum.api.java.applications.usecase;

import forum.api.java.applications.security.PasswordHash;
import forum.api.java.domain.authentication.AuthenticationRepository;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ValidatePasswordResetTokenUseCase {
    private final PasswordHash passwordHash;
    private final AuthenticationRepository authenticationRepository;

    public ValidatePasswordResetTokenUseCase(PasswordHash passwordHash, AuthenticationRepository authenticationRepository) {
        this.passwordHash = passwordHash;
        this.authenticationRepository = authenticationRepository;
    }

    public void execute(String rawToken) throws NoSuchAlgorithmException, InvalidKeyException {
        String tokenHash = passwordHash.hashToken(rawToken);
        authenticationRepository.validatePasswordResetToken(tokenHash);
    }
}
