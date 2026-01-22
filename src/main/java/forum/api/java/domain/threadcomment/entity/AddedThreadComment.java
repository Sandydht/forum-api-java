package forum.api.java.domain.threadcomment.entity;

import forum.api.java.domain.utils.ValidationUtils;

import java.time.Instant;
import java.util.Optional;

public class AddedThreadComment {
    private static final String notContainNeededPropertyErrorMessage = "ADDED_THREAD_COMMENT.NOT_CONTAIN_NEEDED_PROPERTY";

    private final String id;
    private final String content;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final Optional<Instant> deletedAt;
    private final String userId;
    private final String threadId;

    public AddedThreadComment(String id, String content, Instant createdAt, Instant updatedAt, Optional<Instant> deletedAt, String userId, String threadId) {
        verifyPayload(id, content, createdAt, updatedAt, threadId, userId);

        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.userId = userId;
        this.threadId = threadId;
    }

    private static void verifyPayload(String id, String content, Instant createdAt, Instant updatedAt, String userId, String threadId) {
        ValidationUtils.requireNotBlank(id, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(content, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNonNull(createdAt, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNonNull(updatedAt, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(userId, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(threadId, notContainNeededPropertyErrorMessage);
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
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

    public String getUserId() {
        return userId;
    }

    public String getThreadId() {
        return threadId;
    }
}
