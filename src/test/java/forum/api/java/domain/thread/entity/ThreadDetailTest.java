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

@DisplayName("a ThreadDetail entity")
public class ThreadDetailTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        return Stream.of(
                Arguments.of(null, "Title", "Body", new UserThreadDetail(UUID.randomUUID().toString(), "Fullname")),
                Arguments.of(UUID.randomUUID().toString(), null, "Body", new UserThreadDetail(UUID.randomUUID().toString(), "Fullname")),
                Arguments.of(UUID.randomUUID().toString(), "Title", null, new UserThreadDetail(UUID.randomUUID().toString(), "Fullname")),
                Arguments.of("  ", "Title", "Body", new UserThreadDetail(UUID.randomUUID().toString(), "Fullname")),
                Arguments.of(UUID.randomUUID().toString(), "  ", "Body", new UserThreadDetail(UUID.randomUUID().toString(), "Fullname")),
                Arguments.of(UUID.randomUUID().toString(), "Title", "  ", new UserThreadDetail(UUID.randomUUID().toString(), "Fullname"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMissingData")
    @DisplayName("should throw error when payload did not contain needed property")
    public void testNotContainNeededProperty(String id, String title, String body, UserThreadDetail userThreadDetail) {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new ThreadDetail(id, title, body, userThreadDetail)
        );

        Assertions.assertEquals("THREAD_DETAIL.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create ThreadDetail object correctly")
    public void testObjectCorrectly() {
        String id = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";
        UserThreadDetail userThreadDetail = new UserThreadDetail(UUID.randomUUID().toString(), "Fullname");

        ThreadDetail threadDetail = new ThreadDetail(id, title, body, userThreadDetail);

        Assertions.assertEquals(id, threadDetail.getId());
        Assertions.assertEquals(title, threadDetail.getTitle());
        Assertions.assertEquals(body, threadDetail.getBody());
        Assertions.assertEquals(userThreadDetail.getId(), threadDetail.getUserThreadDetail().getId());
        Assertions.assertEquals(userThreadDetail.getFullname(), threadDetail.getUserThreadDetail().getFullname());
    }
}
