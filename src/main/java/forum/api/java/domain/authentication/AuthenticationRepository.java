package forum.api.java.domain.authentication;

import forum.api.java.domain.authentication.entity.AddedPasswordResetToken;

import java.time.Instant;

public interface AuthenticationRepository {
    default void addToken(String username, String token) {
        throw new UnsupportedOperationException("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default void deleteExpiredTokens(Instant now) {
        throw new UnsupportedOperationException("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default void checkAvailabilityToken(String token) {
        throw new UnsupportedOperationException("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default void deleteToken(String token) {
        throw new UnsupportedOperationException("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default void deleteTokenByUserId(String userId) {
        throw new UnsupportedOperationException("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default AddedPasswordResetToken addPasswordResetToken(String userId, String tokenHash, String ipRequest, String userAgent) {
        throw new UnsupportedOperationException("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }
}
