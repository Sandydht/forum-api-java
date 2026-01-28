package forum.api.java.domain.user.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

@DisplayName("UserThreadDetail entity")
public class UserThreadDetailTest {
    private static final String id = UUID.randomUUID().toString();
    private static final String username = "user";
    private static final String fullname = "Fullname";

    private static Stream<Arguments> provideInvalidMissingData() {
        return Stream.of(
                Arguments.of(null, username, fullname),
                Arguments.of(id, null, fullname),
                Arguments.of(id, username, null),
                Arguments.of("  ", username, fullname),
                Arguments.of(id, "  ", fullname),
                Arguments.of(id, username, "  ")
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String id, String username, String fullname) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserThreadDetail(id, username, fullname);
        });

        Assertions.assertEquals("USER_THREAD_DETAIL.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains more than 50 character")
    public void shouldThrowErrorWhenUsernameContainsMoreThan50Character() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserThreadDetail(id, "user".repeat(51), fullname);
        });

        Assertions.assertEquals("USER_THREAD_DETAIL.USERNAME_LIMIT_CHAR", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains restricted character")
    public void shouldThrowErrorWhenUsernameContainsRestrictedCharacter() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserThreadDetail(id, "Invalid Username", fullname);
        });

        Assertions.assertEquals("USER_THREAD_DETAIL.USERNAME_CONTAIN_RESTRICTED_CHARACTER", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        UserThreadDetail userThreadDetail = new UserThreadDetail(id, username, fullname);

        Assertions.assertEquals(id, userThreadDetail.getId());
        Assertions.assertEquals(username, userThreadDetail.getUsername());
        Assertions.assertEquals(fullname, userThreadDetail.getFullname());
    }
}
