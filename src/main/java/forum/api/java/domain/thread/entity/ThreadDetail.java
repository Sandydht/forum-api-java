package forum.api.java.domain.thread.entity;

import forum.api.java.domain.user.entity.UserThreadDetail;
import forum.api.java.domain.utils.ValidationUtils;

import java.time.Instant;
import java.util.Optional;

public class ThreadDetail {
    private static final String notContainNeededPropertyErrorMessage = "THREAD_DETAIL.NOT_CONTAIN_NEEDED_PROPERTY";

    private final String id;
    private final String title;
    private final String body;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final Optional<Instant> deletedAt;
    private final UserThreadDetail owner;

    public ThreadDetail(String id, String title, String body, Instant createdAt, Instant updatedAt, Optional<Instant> deletedAt, UserThreadDetail owner) {
        verifyPayload(id, title, body, createdAt, updatedAt, owner);

        this.id = id;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.owner = owner;
    }

    private static void verifyPayload(String id, String title, String body, Instant createdAt, Instant updatedAt, UserThreadDetail owner) {
        ValidationUtils.requireNotBlank(id, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(title, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(body, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNonNull(createdAt, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNonNull(updatedAt, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNonNull(owner, notContainNeededPropertyErrorMessage);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
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

    public UserThreadDetail getOwner() {
        return owner;
    }
}
