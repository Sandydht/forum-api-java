package forum.api.java.domain.user.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

@DisplayName("a RegisteredUser entity")
public class RegisteredUserTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        return Stream.of(
                Arguments.of(null, "user", "Fullname"),
                Arguments.of(UUID.randomUUID().toString(), null, "Fullname"),
                Arguments.of(UUID.randomUUID().toString(), "", "Fullname"),
                Arguments.of(UUID.randomUUID().toString(), "user", null),
                Arguments.of(UUID.randomUUID().toString(), "user", "  ")
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void testNotContainNeededProperty(String id, String username, String fullname) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisteredUser(id, username, fullname);
        });

        Assertions.assertEquals("REGISTERED_USER.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains more than 50 character")
    public void testUsernameLimitCharacter() {
        String invalidUsername = "invalidusernameinvalidusernameinvalidusernameinvalidusername";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisteredUser(UUID.randomUUID().toString(), invalidUsername, "Fullname");
        });

        Assertions.assertEquals("REGISTERED_USER.USERNAME_LIMIT_CHAR", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains restricted character")
    public void testNotContainRestrictedCharacter() {
        String invalidUsername = "Invalid Username";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisteredUser(UUID.randomUUID().toString(), invalidUsername, "Fullname");
        });

        Assertions.assertEquals("REGISTERED_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER", exception.getMessage());
    }

    @Test
    @DisplayName("should create registerUser object correctly")
    public void testObjectCorrectly() {
        String id = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";

        RegisteredUser registeredUser = new RegisteredUser(id, username, fullname);

        Assertions.assertEquals(id, registeredUser.getId());
        Assertions.assertEquals(username, registeredUser.getUsername());
        Assertions.assertEquals(fullname, registeredUser.getFullname());
    }
}
