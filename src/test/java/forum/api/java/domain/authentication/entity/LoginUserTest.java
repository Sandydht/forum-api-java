package forum.api.java.domain.authentication.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("Login user entity")
public class LoginUserTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        String username = "user";
        String password = "password";

        return Stream.of(
                Arguments.of(null, password),
                Arguments.of(username, null),
                Arguments.of("  ", password),
                Arguments.of(username, "  ")
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String username, String password) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new LoginUser(username, password);
        });

        Assertions.assertEquals("LOGIN_USER.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains more than 50 character")
    public void shouldThrowErrorWhenUsernameContainsMoreThan50Character() {
        String invalidUsername = "invalidusernameinvalidusernameinvalidusernameinvalidusername";
        String password = "password";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new LoginUser(invalidUsername, password);
        });

        Assertions.assertEquals("LOGIN_USER.USERNAME_LIMIT_CHAR", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains restricted character")
    public void shouldThrowErrorWhenUsernameContainsRestrictedCharacter() {
        String invalidUsername = "Invalid Username";
        String password = "password";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new LoginUser(invalidUsername, password);
        });

        Assertions.assertEquals("LOGIN_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        String username = "user";
        String password = "password";

        LoginUser loginUser = new LoginUser(username, password);

        Assertions.assertEquals(username, loginUser.getUsername());
        Assertions.assertEquals(password, loginUser.getPassword());
    }
}
