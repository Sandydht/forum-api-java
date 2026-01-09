package forum.api.java.domain.user.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

@DisplayName("Registered user entity")
public class RegisteredUserTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        String id = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";

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
            new RegisteredUser(id, username, fullname);
        });

        Assertions.assertEquals("REGISTERED_USER.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains more than 50 character")
    public void shouldThrowErrorWhenUsernameContainsMoreThan50Character() {
        String id = UUID.randomUUID().toString();
        String invalidUsername = "invalidusernameinvalidusernameinvalidusernameinvalidusername";
        String fullname = "Fullname";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisteredUser(id, invalidUsername, fullname);
        });

        Assertions.assertEquals("REGISTERED_USER.USERNAME_LIMIT_CHAR", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains restricted character")
    public void shouldThrowErrorWhenUsernameContainsRestrictedCharacter() {
        String id = UUID.randomUUID().toString();
        String invalidUsername = "Invalid Username";
        String fullname = "Fullname";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisteredUser(id, invalidUsername, fullname);
        });

        Assertions.assertEquals("REGISTERED_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        String id = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";

        RegisteredUser registeredUser = new RegisteredUser(id, username, fullname);

        Assertions.assertEquals(id, registeredUser.getId());
        Assertions.assertEquals(username, registeredUser.getUsername());
        Assertions.assertEquals(fullname, registeredUser.getFullname());
    }
}
