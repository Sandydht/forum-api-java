package forum.api.java.domain.thread.entity;

import forum.api.java.domain.user.entity.UserThreadDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;

@DisplayName("Thread detail entity")
public class ThreadDetailTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        String id = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();

        String userId = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        UserThreadDetail userThreadDetail = new UserThreadDetail(userId, username, fullname);

        return Stream.of(
                Arguments.of(null, title, body, createdAt, updatedAt, userThreadDetail),
                Arguments.of(id, null, body, createdAt, updatedAt, userThreadDetail),
                Arguments.of(id, title, null, createdAt, updatedAt, userThreadDetail),
                Arguments.of(id, title, body, null, updatedAt, userThreadDetail),
                Arguments.of(id, title, body, createdAt, null, userThreadDetail),
                Arguments.of(id, title, body, createdAt, updatedAt, null),
                Arguments.of("  ", title, body, createdAt, updatedAt, userThreadDetail),
                Arguments.of(id, "  ", body, createdAt, updatedAt, userThreadDetail),
                Arguments.of(id, title, "  ", createdAt, updatedAt, userThreadDetail)
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String id, String title, String body, Instant createdAt, Instant updatedAt, UserThreadDetail owner) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ThreadDetail(id, title, body, createdAt, updatedAt, owner);
        });

        Assertions.assertEquals("THREAD_DETAIL.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        String id = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();

        String userId = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        UserThreadDetail userThreadDetail = new UserThreadDetail(userId, username, fullname);

        ThreadDetail threadDetail = new ThreadDetail(id, title, body, createdAt, updatedAt, userThreadDetail);

        Assertions.assertEquals(id, threadDetail.getId());
        Assertions.assertEquals(title, threadDetail.getTitle());
        Assertions.assertEquals(body, threadDetail.getBody());
        Assertions.assertEquals(createdAt, threadDetail.getCreatedAt());
        Assertions.assertEquals(updatedAt, threadDetail.getUpdatedAt());
        Assertions.assertEquals(userId, threadDetail.getOwner().getId());
        Assertions.assertEquals(username, threadDetail.getOwner().getUsername());
        Assertions.assertEquals(fullname, threadDetail.getOwner().getFullname());
    }
}
