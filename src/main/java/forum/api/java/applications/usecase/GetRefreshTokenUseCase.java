package forum.api.java.applications.usecase;

import forum.api.java.domain.authentication.AuthenticationRepository;

public class GetRefreshTokenUseCase {
    private final AuthenticationRepository authenticationRepository;

    public GetRefreshTokenUseCase(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public String execute(String username) {
        return authenticationRepository.getTokenByUsername(username);
    }
}
