package forum.api.java.domain.authentication.entity;

import forum.api.java.domain.user.entity.UserEntity;

import java.time.Instant;

public class RefreshTokenEntity {
    private final String id;
    private final UserEntity userEntity;
    private final String token;
    private final Instant expiresAt;

    public RefreshTokenEntity(String id, UserEntity userEntity, String token, Instant expiresAt) {
        verifyPayload(id, userEntity, token, expiresAt);

        this.id = id;
        this.userEntity = userEntity;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    private static void verifyPayload(String id, UserEntity userEntity, String token, Instant expiresAt) {
        requireNotBlank(id);
        requireNotNull(userEntity);
        requireNotBlank(token);
        requireNotNull(expiresAt);
    }

    private static void requireNotBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("REFRESH_TOKEN.NOT_CONTAIN_NEEDED_PROPERTY");
        }
    }

    private static void requireNotNull(Object value) {
        if (value == null) {
            throw new IllegalArgumentException("REFRESH_TOKEN.NOT_CONTAIN_NEEDED_PROPERTY");
        }
    }

    public String getId() {
        return id;
    }

    public UserEntity getUser() {
        return userEntity;
    }

    public String getToken() {
        return token;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
}
