package forum.api.java.applications.security;

import java.util.UUID;

public interface AuthenticationTokenManager {
    default String createAccessToken(UUID id) {
        throw new UnsupportedOperationException("AUTHENTICATION_TOKEN_MANAGER.METHOD_NOT_IMPLEMENTED");
    }

    default String createRefreshToken(UUID id) {
        throw new UnsupportedOperationException("AUTHENTICATION_TOKEN_MANAGER.METHOD_NOT_IMPLEMENTED");
    }
}
