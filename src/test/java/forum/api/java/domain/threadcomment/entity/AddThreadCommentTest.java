package forum.api.java.domain.threadcomment.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

@DisplayName("a AddThreadComment entity")
public class AddThreadCommentTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        return Stream.of(
                Arguments.of( null, UUID.randomUUID().toString(), "Content"),
                Arguments.of(UUID.randomUUID().toString(), null, "Content"),
                Arguments.of(UUID.randomUUID().toString(), UUID.randomUUID().toString(), null),
                Arguments.of("  ", UUID.randomUUID().toString(), "Content"),
                Arguments.of(UUID.randomUUID().toString(), "  ", "Content"),
                Arguments.of(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "  ")
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMissingData")
    @DisplayName("should throw error when payload did not contain needed property")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String userId, String threadId, String content) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new AddThreadComment(userId, threadId, content);
        });

        Assertions.assertEquals("ADD_THREAD_COMMENT.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        String userId = UUID.randomUUID().toString();
        String threadId = UUID.randomUUID().toString();
        String content = "Content";

        AddThreadComment addThreadComment = new AddThreadComment(userId, threadId, content);

        Assertions.assertEquals(userId, addThreadComment.getUserId());
        Assertions.assertEquals(threadId, addThreadComment.getThreadId());
        Assertions.assertEquals(content, addThreadComment.getContent());
    }
}
