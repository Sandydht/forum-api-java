package forum.api.java.domain.thread.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

@DisplayName("Update thread entity")
public class UpdateThreadTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        String threadId = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";

        return Stream.of(
                Arguments.of(null, title, body),
                Arguments.of(threadId, null, body),
                Arguments.of(threadId, title, null),
                Arguments.of("  ", title, body),
                Arguments.of(threadId, "  ", body),
                Arguments.of(threadId, title, "  ")
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String threadId, String title, String body) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UpdateThread(threadId, title, body);
        });

        Assertions.assertEquals("UPDATE_THREAD.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        String threadId = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";

        UpdateThread updateThread = new UpdateThread(threadId, title, body);

        Assertions.assertEquals(threadId, updateThread.getThreadId());
        Assertions.assertEquals(title, updateThread.getTitle());
        Assertions.assertEquals(body, updateThread.getBody());
    }
}
