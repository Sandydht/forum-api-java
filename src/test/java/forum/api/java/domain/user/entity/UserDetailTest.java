package forum.api.java.domain.user.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

@DisplayName("User detail entity")
public class UserDetailTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        String id = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        String password = "password";

        return Stream.of(
                Arguments.of(null, username, fullname, password),
                Arguments.of(id, null, fullname, password),
                Arguments.of(id, username, null, password),
                Arguments.of(id, username, fullname, null),
                Arguments.of("  ", username, fullname, password),
                Arguments.of(id, "  ", fullname, password),
                Arguments.of(id, username, "  ", password),
                Arguments.of(id, username, fullname, "  ")
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String id, String username, String fullname, String password) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserDetail(id, username, fullname, password);
        });

        Assertions.assertEquals("USER_DETAIL.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains more than 50 character")
    public void shouldThrowErrorWhenUsernameContainsMoreThan50Character() {
        String id = UUID.randomUUID().toString();
        String invalidUsername = "invalidusernameinvalidusernameinvalidusernameinvalidusername";
        String fullname = "Fullname";
        String password = "password";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserDetail(id, invalidUsername, fullname, password);
        });

        Assertions.assertEquals("USER_DETAIL.USERNAME_LIMIT_CHAR", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains restricted character")
    public void shouldThrowErrorWhenUsernameContainsRestrictedCharacter() {
        String id = UUID.randomUUID().toString();
        String invalidUsername = "Invalid Username";
        String fullname = "Fullname";
        String password = "password";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserDetail(id, invalidUsername, fullname, password);
        });

        Assertions.assertEquals("USER_DETAIL.USERNAME_CONTAIN_RESTRICTED_CHARACTER", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        String id = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        String password = "password";

        UserDetail userDetail = new UserDetail(id, username, fullname, password);

        Assertions.assertEquals(id, userDetail.getId());
        Assertions.assertEquals(username, userDetail.getUsername());
        Assertions.assertEquals(fullname, userDetail.getFullname());
        Assertions.assertEquals(password, userDetail.getPassword());
    }
}
