package forum.api.java.domain.authentication.entity;

import forum.api.java.domain.utils.ValidationUtils;

import java.time.Instant;
import java.util.Optional;

public class AddedPasswordResetToken {
    private static final String notContainNeededPropertyErrorMessage = "ADDED_PASSWORD_RESET_TOKEN.NOT_CONTAIN_NEEDED_PROPERTY";
    private static final String ipv4InvalidErrorMessage = "ADDED_PASSWORD_RESET_TOKEN.IP_V4_IS_INVALID";

    private final String id;
    private final String userId;
    private final String tokenHash;
    private final Instant expiresAt;
    private final Optional<Instant> usedAt;
    private final String ipRequest;
    private final String userAgent;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final Optional<Instant> deletedAt;

    public AddedPasswordResetToken(
            String id,
            String userId,
            String tokenHash,
            Instant expiresAt,
            Optional<Instant> usedAt,
            String ipRequest,
            String userAgent,
            Instant createdAt,
            Instant updatedAt,
            Optional<Instant> deletedAt
    ) {
        verifyPayload(id, userId, tokenHash, expiresAt, ipRequest, userAgent, createdAt, updatedAt);

        this.id = id;
        this.userId = userId;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
        this.usedAt = usedAt;
        this.ipRequest = ipRequest;
        this.userAgent = userAgent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    private static void verifyPayload(String id, String userId, String tokenHash, Instant expiresAt, String ipRequest, String userAgent, Instant createdAt, Instant updatedAt) {
        ValidationUtils.requireNotBlank(id, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(userId, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(tokenHash, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(ipRequest, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(userAgent, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNonNull(expiresAt, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNonNull(createdAt, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNonNull(updatedAt, notContainNeededPropertyErrorMessage);
        ValidationUtils.ipv4Validation(ipRequest, ipv4InvalidErrorMessage);
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public Optional<Instant> getUsedAt() {
        return usedAt;
    }

    public String getIpRequest() {
        return ipRequest;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Optional<Instant> getDeletedAt() {
        return deletedAt;
    }
}
