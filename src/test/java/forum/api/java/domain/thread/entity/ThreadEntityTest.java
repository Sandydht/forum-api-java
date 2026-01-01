package forum.api.java.domain.thread.entity;

import forum.api.java.domain.user.entity.UserEntity;
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
        UserEntity userEntity = new UserEntity(UUID.randomUUID().toString(), "user", "Fullname", "password");
        String title = "Title";
        String body = "Body";

        return Stream.of(
                Arguments.of(null, userEntity, title, body),
                Arguments.of(id, null, title, body),
                Arguments.of(id, userEntity, null, body),
                Arguments.of(id, userEntity, title, null),
                Arguments.of("  ", userEntity, title, body),
                Arguments.of(id, userEntity, "  ", body)
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String id, UserEntity userEntity, String title, String body) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ThreadEntity(id, userEntity, title, body);
        });

        Assertions.assertEquals("THREAD.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        String id = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        String password = "password";

        UserEntity userEntity = new UserEntity(id, username, fullname, password);

        String threadId = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";

        ThreadEntity threadEntity = new ThreadEntity(threadId, userEntity, title, body);

        Assertions.assertEquals(threadId, threadEntity.getId());
        Assertions.assertEquals(userEntity.getId(), threadEntity.getUser().getId());
        Assertions.assertEquals(userEntity.getUsername(), threadEntity.getUser().getUsername());
        Assertions.assertEquals(userEntity.getFullname(), threadEntity.getUser().getFullname());
        Assertions.assertEquals(userEntity.getPassword(), threadEntity.getUser().getPassword());
        Assertions.assertEquals(title, threadEntity.getTitle());
        Assertions.assertEquals(body, threadEntity.getBody());
    }
}
