package forum.api.java.domain.user.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("Register user entity")
public class RegisterUserTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        String username = "user";
        String fullname = "Fullname";
        String password = "password123";
        String captchaToken = "captcha-token";

        return Stream.of(
                Arguments.of(null, fullname, password, captchaToken),
                Arguments.of(username, null, password, captchaToken),
                Arguments.of(username, fullname, null, captchaToken),
                Arguments.of(username, fullname, password, null),
                Arguments.of("  ", fullname, password, captchaToken),
                Arguments.of(username, "  ", password, captchaToken),
                Arguments.of(username, fullname, "  ", captchaToken),
                Arguments.of(username, fullname, password, "  ")
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String username, String fullname, String password, String captchaToken) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterUser(username, fullname, password, captchaToken);
        });

        Assertions.assertEquals("REGISTER_USER.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains more than 50 character")
    public void shouldThrowErrorWhenUsernameContainsMoreThan50Character() {
        String invalidUsername = "invalidusernameinvalidusernameinvalidusernameinvalidusername";
        String fullname = "Fullname";
        String password = "password123";
        String captchaToken = "captcha-token";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterUser(invalidUsername, fullname, password, captchaToken);
        });

        Assertions.assertEquals("REGISTER_USER.USERNAME_LIMIT_CHAR", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains restricted character")
    public void shouldThrowErrorWhenUsernameContainsRestrictedCharacter() {
        String invalidUsername = "Invalid Username";
        String fullname = "Fullname";
        String password = "password123";
        String captchaToken = "captcha-token";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterUser(invalidUsername, fullname, password, captchaToken);
        });

        Assertions.assertEquals("REGISTER_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error if the password is less than 8 characters")
    public void shouldThrowErrorIfPasswordIsLessThan8Characters() {
        String username = "user";
        String fullname = "Fullname";
        String password = "secret1";
        String captchaToken = "captcha-token";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterUser(username, fullname, password, captchaToken);
        });

        Assertions.assertEquals("REGISTER_USER.PASSWORD_MUST_BE_AT_LEAST_8_CHARACTERS", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error if the password not contain letters and numbers")
    public void shouldThrowErrorIfThePasswordNotContainLettersAndNumbers() {
        String username = "user";
        String fullname = "Fullname";
        String password = "password";
        String captchaToken = "captcha-token";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterUser(username, fullname, password, captchaToken);
        });

        Assertions.assertEquals("REGISTER_USER.PASSWORD_MUST_CONTAIN_LETTERS_AND_NUMBERS", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error if the password contain space")
    public void shouldThrowErrorIfThePasswordContainSpace() {
        String username = "user";
        String fullname = "Fullname";
        String password = "pass word123";
        String captchaToken = "captcha-token";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterUser(username, fullname, password, captchaToken);
        });

        Assertions.assertEquals("REGISTER_USER.PASSWORD_MUST_NOT_CONTAIN_SPACE", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        String username = "user";
        String fullname = "Fullname";
        String password = "password1234";
        String captchaToken = "captcha-token";

        RegisterUser registerUser = new RegisterUser(username, fullname, password, captchaToken);

        Assertions.assertEquals(username, registerUser.getUsername());
        Assertions.assertEquals(fullname, registerUser.getFullname());
        Assertions.assertEquals(password, registerUser.getPassword());
        Assertions.assertEquals(captchaToken, registerUser.getCaptchaToken());
    }
}
