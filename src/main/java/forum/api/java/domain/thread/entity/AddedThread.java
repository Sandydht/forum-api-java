package forum.api.java.domain.thread.entity;

import forum.api.java.domain.utils.ValidationUtils;

import java.time.Instant;
import java.util.Optional;

public class AddedThread {
    private static final String notContainNeededPropertyErrorMessage = "ADDED_THREAD.NOT_CONTAIN_NEEDED_PROPERTY";

    private final String id;
    private final String title;
    private final String body;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final Optional<Instant> deletedAt;

    public AddedThread(String id, String title, String body, Instant createdAt, Instant updatedAt, Optional<Instant> deletedAt) {
        verifyPayload(id, title, body, createdAt, updatedAt);

        this.id = id;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    private static void verifyPayload(String id, String title, String body, Instant createdAt, Instant updatedAt) {
        ValidationUtils.requireNotBlank(id, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(title, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(body, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNonNull(createdAt, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNonNull(updatedAt, notContainNeededPropertyErrorMessage);
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
}
