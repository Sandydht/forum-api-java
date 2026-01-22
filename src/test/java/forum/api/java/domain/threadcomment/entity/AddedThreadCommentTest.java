package forum.api.java.domain.threadcomment.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@DisplayName("Added thread comment entity")
public class AddedThreadCommentTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        String id = UUID.randomUUID().toString();
        String content = "Content";
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();
        Optional<Instant> deletedAt = Optional.empty();
        String userId = UUID.randomUUID().toString();
        String threadId = UUID.randomUUID().toString();

        return Stream.of(
                Arguments.of(null, content, createdAt, updatedAt, deletedAt, userId, threadId),
                Arguments.of(id, null, createdAt, updatedAt, deletedAt, userId, threadId),
                Arguments.of(id, content, null, updatedAt, deletedAt, userId, threadId),
                Arguments.of(id, content, createdAt, null, deletedAt, userId, threadId),
                Arguments.of(id, content, createdAt, updatedAt, deletedAt, null, threadId),
                Arguments.of(id, content, createdAt, updatedAt, deletedAt, userId, null)
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String id, String content, Instant createdAt, Instant updatedAt, Optional<Instant> deletedAt, String userId, String threadId) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new AddedThreadComment(id, content, createdAt, updatedAt, deletedAt, userId, threadId);
        });

        Assertions.assertEquals("ADDED_THREAD_COMMENT.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        String id = UUID.randomUUID().toString();
        String content = "Content";
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();
        Optional<Instant> deletedAt = Optional.empty();
        String userId = UUID.randomUUID().toString();
        String threadId = UUID.randomUUID().toString();

        AddedThreadComment addedThreadComment = new AddedThreadComment(id, content, createdAt, updatedAt, deletedAt, userId, threadId);

        Assertions.assertEquals(id, addedThreadComment.getId());
        Assertions.assertEquals(content, addedThreadComment.getContent());
        Assertions.assertEquals(createdAt, addedThreadComment.getCreatedAt());
        Assertions.assertEquals(updatedAt, addedThreadComment.getUpdatedAt());
        Assertions.assertEquals(deletedAt, addedThreadComment.getDeletedAt());
        Assertions.assertEquals(userId, addedThreadComment.getUserId());
        Assertions.assertEquals(threadId, addedThreadComment.getThreadId());
    }
}
