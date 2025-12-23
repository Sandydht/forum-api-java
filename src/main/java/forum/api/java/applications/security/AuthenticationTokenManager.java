package forum.api.java.applications.security;

public interface AuthenticationTokenManager {
    default String createAccessToken(String id) {
        throw new UnsupportedOperationException("AUTHENTICATION_TOKEN_MANAGER.METHOD_NOT_IMPLEMENTED");
    }

    default String createRefreshToken(String id) {
        throw new UnsupportedOperationException("AUTHENTICATION_TOKEN_MANAGER.METHOD_NOT_IMPLEMENTED");
    }

    default String decodeJWTPayload(String token) {
        throw new UnsupportedOperationException("AUTHENTICATION_TOKEN_MANAGER.METHOD_NOT_IMPLEMENTED");
    }
}
