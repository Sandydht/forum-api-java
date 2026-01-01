package forum.api.java.applications.usecase;

import forum.api.java.applications.security.AuthenticationTokenManager;
import forum.api.java.domain.authentication.AuthenticationRepository;

public class RefreshAuthenticationUseCase {
    private final AuthenticationRepository authenticationRepository;
    private final AuthenticationTokenManager authenticationTokenManager;

    public RefreshAuthenticationUseCase(AuthenticationRepository authenticationRepository, AuthenticationTokenManager authenticationTokenManager) {
        this.authenticationRepository = authenticationRepository;
        this.authenticationTokenManager = authenticationTokenManager;
    }

    public String execute(String refreshToken) {
        authenticationTokenManager.verifyToken(refreshToken);
        authenticationRepository.checkAvailabilityToken(refreshToken);
        String userId = authenticationTokenManager.decodeJWTPayload(refreshToken);
        return authenticationTokenManager.createAccessToken(userId);
    }
}
