package forum.api.java.domain.authentication.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("LoginUser entity")
public class LoginUserTest {
    private static final String username = "user";
    private static final String password = "password123";
    private static final String captchaToken = "captcha-token";

    private static Stream<Arguments> provideInvalidMissingData() {
        return Stream.of(
                Arguments.of(null, password, captchaToken),
                Arguments.of(username, null, captchaToken),
                Arguments.of(username, password, null),
                Arguments.of("  ", password, captchaToken),
                Arguments.of(username, "  ", captchaToken),
                Arguments.of(username, password, "  ")
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String username, String password, String captchaToken) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new LoginUser(username, password, captchaToken);
        });

        Assertions.assertEquals("LOGIN_USER.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains more than 50 character")
    public void shouldThrowErrorWhenUsernameContainsMoreThan50Character() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new LoginUser("user".repeat(51), password, captchaToken);
        });

        Assertions.assertEquals("LOGIN_USER.USERNAME_LIMIT_CHAR", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains restricted character")
    public void shouldThrowErrorWhenUsernameContainsRestrictedCharacter() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new LoginUser("Invalid Username", password, captchaToken);
        });

        Assertions.assertEquals("LOGIN_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error if the password is less than 8 characters")
    public void shouldThrowErrorIfPasswordIsLessThan8Characters() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new LoginUser(username, "secret1", captchaToken);
        });

        Assertions.assertEquals("LOGIN_USER.PASSWORD_MUST_BE_AT_LEAST_8_CHARACTERS", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error if the password not contain letters and numbers")
    public void shouldThrowErrorIfThePasswordNotContainLettersAndNumbers() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new LoginUser(username, "password", captchaToken);
        });

        Assertions.assertEquals("LOGIN_USER.PASSWORD_MUST_CONTAIN_LETTERS_AND_NUMBERS", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error if the password contain space")
    public void shouldThrowErrorIfThePasswordContainSpace() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new LoginUser(username, "pass word123", captchaToken);
        });

        Assertions.assertEquals("LOGIN_USER.PASSWORD_MUST_NOT_CONTAIN_SPACE", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        LoginUser loginUser = new LoginUser(username, password, captchaToken);

        Assertions.assertEquals(username, loginUser.getUsername());
        Assertions.assertEquals(password, loginUser.getPassword());
        Assertions.assertEquals(captchaToken, loginUser.getCaptchaToken());
    }
}
