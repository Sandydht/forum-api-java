package forum.api.java.applications.usecase;

import forum.api.java.domain.authentication.AuthenticationRepository;

import java.time.Instant;

public class CleanupExpiredTokenUseCase {
    private final AuthenticationRepository authenticationRepository;

    public CleanupExpiredTokenUseCase(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public void execute() {
        authenticationRepository.deleteExpiredTokens(Instant.now());
    }
}
