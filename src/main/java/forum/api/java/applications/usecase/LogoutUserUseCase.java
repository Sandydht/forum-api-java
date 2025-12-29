package forum.api.java.applications.usecase;

import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.authentication.entity.LogoutAuth;

public class LogoutUserUseCase {
    private final AuthenticationRepository authenticationRepository;

    public LogoutUserUseCase(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public void execute(LogoutAuth logoutAuth) {
        authenticationRepository.checkAvailabilityToken(logoutAuth.getRefreshToken());
        authenticationRepository.deleteToken(logoutAuth.getRefreshToken());
    }
}
