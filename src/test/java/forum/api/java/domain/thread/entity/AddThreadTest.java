package forum.api.java.domain.thread.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

@DisplayName("Add thread entity")
public class AddThreadTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        String userId = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";

        return Stream.of(
                Arguments.of(null, title, body),
                Arguments.of(userId, null, body),
                Arguments.of(userId, title, null),
                Arguments.of("  ", title, body),
                Arguments.of(userId, "  ", body),
                Arguments.of(userId, title, "  ")
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String userId, String title, String body) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new AddThread(userId, title, body);
        });

        Assertions.assertEquals("ADD_THREAD.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        String userId = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";

        AddThread addThread = new AddThread(userId, title, body);

        Assertions.assertEquals(userId, addThread.getUserId());
        Assertions.assertEquals(title, addThread.getTitle());
        Assertions.assertEquals(body, addThread.getBody());
    }
}
