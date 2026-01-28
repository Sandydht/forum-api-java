package forum.api.java.domain.authentication.entity;

import forum.api.java.domain.user.entity.UserDetail;
import forum.api.java.domain.utils.ValidationUtils;

import java.time.Instant;
import java.util.Optional;

public class PasswordResetTokenDetail {
    private static final String notContainNeededPropertyErrorMessage = "PASSWORD_RESET_TOKEN_DETAIL.NOT_CONTAIN_NEEDED_PROPERTY";
    private static final String ipInvalidErrorMessage = "PASSWORD_RESET_TOKEN_DETAIL.IP_ADDRESS_IS_INVALID";

    private final String id;
    private final UserDetail user;
    private final String tokenHash;
    private final Instant expiresAt;
    private final Optional<Instant> usedAt;
    private final String ipRequest;
    private final String userAgent;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final Optional<Instant> deletedAt;

    public PasswordResetTokenDetail(
            String id,
            UserDetail user,
            String tokenHash,
            Instant expiresAt,
            Optional<Instant> usedAt,
            String ipRequest,
            String userAgent,
            Instant createdAt,
            Instant updatedAt,
            Optional<Instant> deletedAt
    ) {
        verifyPayload(id, user, tokenHash, expiresAt, ipRequest, userAgent, createdAt, updatedAt);

        this.id = id;
        this.user = user;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
        this.usedAt = usedAt;
        this.ipRequest = ipRequest;
        this.userAgent = userAgent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    private static void verifyPayload(
            String id,
            UserDetail user,
            String tokenHash,
            Instant expiresAt,
            String ipRequest,
            String userAgent,
            Instant createdAt,
            Instant updatedAt
    ) {
        ValidationUtils.requireNotBlank(id, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNonNull(user, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(tokenHash, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNonNull(expiresAt, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(ipRequest, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(userAgent, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNonNull(createdAt, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNonNull(updatedAt, notContainNeededPropertyErrorMessage);

        ValidationUtils.validateIpAddress(ipRequest, ipInvalidErrorMessage);
    }

    public String getId() {
        return id;
    }

    public UserDetail getUser() {
        return user;
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
