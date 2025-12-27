package forum.api.java.domain.user.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("a RegisterUser entity")
public class RegisterUserTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        return Stream.of(
                Arguments.of(null, "Fullname", "password"),
                Arguments.of("user", null, "password"),
                Arguments.of("user", "", "password"),
                Arguments.of("user", "Fullname", null),
                Arguments.of("user", "Fullname", "  ")
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMissingData")
    @DisplayName("should throw error when payload did not contain needed property")
    public void testNotContainNeededProperty(String username, String fullname, String password) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterUser(username, fullname, password);
        });

        Assertions.assertEquals("REGISTER_USER.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains more than 50 character")
    public void testUsernameLimitCharacter() {
        String invalidUsername = "invalidusernameinvalidusernameinvalidusernameinvalidusername";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterUser(invalidUsername, "User", "Password");
        });

        Assertions.assertEquals("REGISTER_USER.USERNAME_LIMIT_CHAR", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains restricted character")
    public void testNotContainRestrictedCharacter() {
        String invalidUsername = "Invalid Username";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterUser(invalidUsername, "User", "Password");
        });

        Assertions.assertEquals("REGISTER_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER", exception.getMessage());
    }

    @Test
    @DisplayName("should create registerUser object correctly")
    public void testObjectCorrectly() {
        String username = "user";
        String fullname = "Valid Fullname";
        String password = "password";

        RegisterUser registerUser = new RegisterUser(username, fullname, password);

        Assertions.assertEquals(username, registerUser.getUsername());
        Assertions.assertEquals(fullname, registerUser.getFullname());
        Assertions.assertEquals(password, registerUser.getPassword());
    }
}
