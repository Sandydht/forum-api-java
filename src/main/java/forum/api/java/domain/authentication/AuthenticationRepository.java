package forum.api.java.domain.authentication;

import forum.api.java.domain.user.entity.UserEntity;

import java.time.Instant;

public interface AuthenticationRepository {
    default void addToken(UserEntity userEntity, String token) {
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
}
