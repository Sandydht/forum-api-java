package forum.api.java.domain.authentication.entity;

import java.time.Instant;

public class RefreshTokenEntity {
    private final String id;
    private final String userId;
    private final String token;
    private final Instant expiresAt;

    public RefreshTokenEntity(String id, String userId, String token, Instant expiresAt) {
        verifyPayload(id, userId, token, expiresAt);

        this.id = id;
        this.userId = userId;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    private static void verifyPayload(String id, String userId, String token, Instant expiresAt) {
        requireNotBlank(id);
        requireNotBlank(userId);
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

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
}
