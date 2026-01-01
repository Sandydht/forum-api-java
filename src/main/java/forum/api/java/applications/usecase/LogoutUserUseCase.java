package forum.api.java.applications.usecase;

import forum.api.java.domain.authentication.AuthenticationRepository;

public class LogoutUserUseCase {
    private final AuthenticationRepository authenticationRepository;

    public LogoutUserUseCase(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public void execute(String userId) {
        authenticationRepository.deleteTokenByUserId(userId);
    }
}
