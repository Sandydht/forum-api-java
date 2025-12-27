package forum.api.java.domain.thread.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("a AddThread entity")
public class AddThreadTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        return Stream.of(
                Arguments.of(null, "Body"),
                Arguments.of("Title", null),
                Arguments.of("", "Body"),
                Arguments.of("Title", ""),
                Arguments.of("Title", "  ")
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMissingData")
    @DisplayName("should throw error when payload did not contain needed property")
    public void testNotContainNeededProperty(String title, String body) {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new AddThread(title, body)
        );

        Assertions.assertEquals("ADD_THREAD.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create addThread object correctly")
    public void testObjectCorrectly() {
        String title = "Title";
        String body = "Body";

        AddThread addThread = new AddThread(title, body);

        Assertions.assertEquals(title, addThread.getTitle());
        Assertions.assertEquals(body, addThread.getBody());
    }
}
