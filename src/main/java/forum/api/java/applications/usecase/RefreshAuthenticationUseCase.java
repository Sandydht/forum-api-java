package forum.api.java.applications.usecase;

import forum.api.java.applications.security.AuthenticationTokenManager;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.authentication.entity.RefreshAuth;

public class RefreshAuthenticationUseCase {
    private final AuthenticationRepository authenticationRepository;
    private final AuthenticationTokenManager authenticationTokenManager;

    public RefreshAuthenticationUseCase(AuthenticationRepository authenticationRepository, AuthenticationTokenManager authenticationTokenManager) {
        this.authenticationRepository = authenticationRepository;
        this.authenticationTokenManager = authenticationTokenManager;
    }

    public String execute(RefreshAuth refreshAuth) {
        authenticationTokenManager.verifyToken(refreshAuth.getRefreshToken());
        authenticationRepository.checkAvailabilityToken(refreshAuth.getRefreshToken());
        String userId = authenticationTokenManager.decodeJWTPayload(refreshAuth.getRefreshToken());
        return authenticationTokenManager.createAccessToken(userId);
    }
}
