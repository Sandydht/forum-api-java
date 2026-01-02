package forum.api.java.domain.thread.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

public class ThreadEntityTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        String id = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";

        return Stream.of(
                Arguments.of(null, userId, title, body),
                Arguments.of(id, null, title, body),
                Arguments.of(id, userId, null, body),
                Arguments.of(id, userId, title, null),
                Arguments.of("  ", userId, title, body),
                Arguments.of(id, userId, "  ", body)
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String id, String userId, String title, String body) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ThreadEntity(id, userId, title, body);
        });

        Assertions.assertEquals("THREAD.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        String id = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";

        ThreadEntity threadEntity = new ThreadEntity(id, userId, title, body);

        Assertions.assertEquals(id, threadEntity.getId());
        Assertions.assertEquals(userId, threadEntity.getUserId());
        Assertions.assertEquals(title, threadEntity.getTitle());
        Assertions.assertEquals(body, threadEntity.getBody());
    }
}
