package forum.api.java.domain.thread.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

public class AddedThreadTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        return Stream.of(
                Arguments.of(null, "Title", "Body"),
                Arguments.of(UUID.randomUUID().toString(), null, "Body"),
                Arguments.of(UUID.randomUUID().toString(), "", "Body"),
                Arguments.of(UUID.randomUUID().toString(), "Title", null),
                Arguments.of(UUID.randomUUID().toString(), "Title", "  ")
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMissingData")
    @DisplayName("should throw error when payload did not contain needed property")
    public void testNotContainNeededProperty(String id, String title, String body) {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new AddedThread(id, title, body)
        );

        Assertions.assertEquals("ADDED_THREAD.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create addThread object correctly")
    public void testObjectCorrectly() {
        String id = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";

        AddedThread addedThread = new AddedThread(id, title, body);

        Assertions.assertEquals(id, addedThread.getId());
        Assertions.assertEquals(title, addedThread.getTitle());
        Assertions.assertEquals(body, addedThread.getBody());
    }
}
