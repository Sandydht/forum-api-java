package forum.api.java.domain.thread.entity;

import forum.api.java.domain.user.entity.UserThreadDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

@DisplayName("Thread detail entity")
public class ThreadDetailTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        String id = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";

        String userId = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        UserThreadDetail userThreadDetail = new UserThreadDetail(userId, username, fullname);

        return Stream.of(
                Arguments.of(null, title, body, userThreadDetail),
                Arguments.of(id, null, body, userThreadDetail),
                Arguments.of(id, title, null, userThreadDetail),
                Arguments.of(id, title, body, null),
                Arguments.of("  ", title, body, userThreadDetail),
                Arguments.of(id, "  ", body, userThreadDetail),
                Arguments.of(id, title, "  ", userThreadDetail)
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String id, String title, String body, UserThreadDetail owner) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ThreadDetail(id, title, body, owner);
        });

        Assertions.assertEquals("THREAD_DETAIL.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        String id = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";

        String userId = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        UserThreadDetail userThreadDetail = new UserThreadDetail(userId, username, fullname);

        ThreadDetail threadDetail = new ThreadDetail(id, title, body, userThreadDetail);

        Assertions.assertEquals(id, threadDetail.getId());
        Assertions.assertEquals(title, threadDetail.getTitle());
        Assertions.assertEquals(body, threadDetail.getBody());
        Assertions.assertEquals(userId, threadDetail.getOwner().getId());
        Assertions.assertEquals(username, threadDetail.getOwner().getUsername());
        Assertions.assertEquals(fullname, threadDetail.getOwner().getFullname());
    }
}
