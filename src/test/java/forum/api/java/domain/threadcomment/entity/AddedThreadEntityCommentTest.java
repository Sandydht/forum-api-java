package forum.api.java.domain.threadcomment.entity;

import forum.api.java.domain.user.entity.UserThreadDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

@DisplayName("a AddedThreadComment entity")
public class AddedThreadEntityCommentTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        return Stream.of(
                Arguments.of(null, "test-comment", new UserThreadDetail(UUID.randomUUID().toString(), "Fullname")),
                Arguments.of(UUID.randomUUID().toString(), null, new UserThreadDetail(UUID.randomUUID().toString(), "Fullname")),
                Arguments.of("  ", "test-comment", new UserThreadDetail(UUID.randomUUID().toString(), "Fullname")),
                Arguments.of(UUID.randomUUID().toString(), "  ", new UserThreadDetail(UUID.randomUUID().toString(), "Fullname"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMissingData")
    @DisplayName("should throw error when payload did not contain needed property")
    public void testNotContainNeededProperty(String id, String content, UserThreadDetail userThreadDetail) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new AddedThreadComment(id, content, userThreadDetail);
        });

        Assertions.assertEquals("ADDED_THREAD_COMMENT.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create addedThreadComment object correctly")
    public void testCreateObjectCorrectly() {
        String id = UUID.randomUUID().toString();
        String content = "content";

        String userId = UUID.randomUUID().toString();
        String fullname = "Fullname";

        UserThreadDetail userThreadDetail = new UserThreadDetail(userId, fullname);
        AddedThreadComment addedThreadComment = new AddedThreadComment(id, content, userThreadDetail);

        Assertions.assertEquals(id, addedThreadComment.getId());
        Assertions.assertEquals(content, addedThreadComment.getContent());
        Assertions.assertEquals(userId, addedThreadComment.getUserThreadDetail().getId());
        Assertions.assertEquals(fullname, addedThreadComment.getUserThreadDetail().getFullname());
    }
}
