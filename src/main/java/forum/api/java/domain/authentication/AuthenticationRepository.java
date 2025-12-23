package forum.api.java.domain.authentication;

import forum.api.java.domain.user.entity.UserDetail;

import java.time.Instant;

public interface AuthenticationRepository {
    default void addToken(UserDetail userDetail, String token, Instant expiresAt) {
        throw new UnsupportedOperationException("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default void deleteExpiredTokens(Instant now) {
        throw new UnsupportedOperationException("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }
}
