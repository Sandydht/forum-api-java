package forum.api.java.domain.thread.entity;

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

@DisplayName("Added thread entity")
public class AddedThreadTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        String id = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();
        Optional<Instant> deletedAt = Optional.empty();

        return Stream.of(
                Arguments.of(null, title, body, createdAt, updatedAt, deletedAt),
                Arguments.of(id, null, body, createdAt, updatedAt, deletedAt),
                Arguments.of(id, title, null, createdAt, updatedAt, deletedAt),
                Arguments.of(id, title, body, null, updatedAt, deletedAt),
                Arguments.of(id, title, body, createdAt, null, deletedAt),
                Arguments.of("  ", title, body, createdAt, updatedAt, deletedAt),
                Arguments.of(id, "  ", body, createdAt, updatedAt, deletedAt),
                Arguments.of(id, title, "  ", createdAt, updatedAt, deletedAt)
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String id, String title, String body, Instant createdAt, Instant updatedAt, Optional<Instant> deletedAt) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new AddedThread(id, title, body, createdAt, updatedAt, deletedAt);
        });

        Assertions.assertEquals("ADDED_THREAD.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        String id = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();
        Optional<Instant> deletedAt = Optional.empty();

        AddedThread addedThread = new AddedThread(id, title, body, createdAt, updatedAt, deletedAt);

        Assertions.assertEquals(id, addedThread.getId());
        Assertions.assertEquals(title, addedThread.getTitle());
        Assertions.assertEquals(body, addedThread.getBody());
        Assertions.assertEquals(createdAt, addedThread.getCreatedAt());
        Assertions.assertEquals(updatedAt, addedThread.getUpdatedAt());
        Assertions.assertEquals(deletedAt, addedThread.getDeletedAt());
    }
}
