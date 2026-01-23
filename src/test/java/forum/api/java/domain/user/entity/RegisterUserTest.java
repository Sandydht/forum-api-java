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
        String email = "example@email.com";
        String phoneNumber = "6281123123123";
        String fullname = "Fullname";
        String password = "password123";
        String captchaToken = "captcha-token";

        return Stream.of(
                Arguments.of(null, email, phoneNumber, fullname, password, captchaToken),
                Arguments.of(username, null, phoneNumber, fullname, password, captchaToken),
                Arguments.of(username, email, null, fullname, password, captchaToken),
                Arguments.of(username, email, phoneNumber, null, password, captchaToken),
                Arguments.of(username, email, phoneNumber, fullname, null, captchaToken),
                Arguments.of(username, email, phoneNumber, fullname, password, null),
                Arguments.of("  ", email, phoneNumber, fullname, password, captchaToken),
                Arguments.of(username, "  ", phoneNumber, fullname, password, captchaToken),
                Arguments.of(username, email, "  ", fullname, password, captchaToken),
                Arguments.of(username, email, phoneNumber, "  ", password, captchaToken),
                Arguments.of(username, email, phoneNumber, fullname, "  ", captchaToken),
                Arguments.of(username, email, phoneNumber, fullname, password, "  ")
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String username, String email, String phoneNumber, String fullname, String password, String captchaToken) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterUser(username, email, phoneNumber, fullname, password, captchaToken);
        });

        Assertions.assertEquals("REGISTER_USER.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains more than 50 character")
    public void shouldThrowErrorWhenUsernameContainsMoreThan50Character() {
        String invalidUsername = "invalidusernameinvalidusernameinvalidusernameinvalidusername";
        String email = "example@email.com";
        String phoneNumber = "6281123123123";
        String fullname = "Fullname";
        String password = "password123";
        String captchaToken = "captcha-token";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterUser(invalidUsername, email, phoneNumber, fullname, password, captchaToken);
        });

        Assertions.assertEquals("REGISTER_USER.USERNAME_LIMIT_CHAR", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains restricted character")
    public void shouldThrowErrorWhenUsernameContainsRestrictedCharacter() {
        String invalidUsername = "Invalid Username";
        String email = "example@email.com";
        String phoneNumber = "6281123123123";
        String fullname = "Fullname";
        String password = "password123";
        String captchaToken = "captcha-token";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterUser(invalidUsername, email, phoneNumber, fullname, password, captchaToken);
        });

        Assertions.assertEquals("REGISTER_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when email is invalid")
    public void shouldThrowErrorWhenEmailIsInvalid() {
        String username = "user";
        String email = "invalid email";
        String phoneNumber = "6281123123123";
        String fullname = "Fullname";
        String password = "password123";
        String captchaToken = "captcha-token";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterUser(username, email, phoneNumber, fullname, password, captchaToken);
        });

        Assertions.assertEquals("REGISTER_USER.EMAIL_IS_INVALID", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when phone number is invalid")
    public void shouldThrowErrorWhenPhoneNumberIsInvalid() {
        String username = "user";
        String email = "example@email.com";
        String phoneNumber = "invalid phone number";
        String fullname = "Fullname";
        String password = "password123";
        String captchaToken = "captcha-token";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterUser(username, email, phoneNumber, fullname, password, captchaToken);
        });

        Assertions.assertEquals("REGISTER_USER.PHONE_NUMBER_IS_INVALID", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error if the password is less than 8 characters")
    public void shouldThrowErrorIfPasswordIsLessThan8Characters() {
        String username = "user";
        String email = "example@email.com";
        String phoneNumber = "6281123123123";
        String fullname = "Fullname";
        String password = "secret1";
        String captchaToken = "captcha-token";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterUser(username, email, phoneNumber, fullname, password, captchaToken);
        });

        Assertions.assertEquals("REGISTER_USER.PASSWORD_MUST_BE_AT_LEAST_8_CHARACTERS", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error if the password not contain letters and numbers")
    public void shouldThrowErrorIfThePasswordNotContainLettersAndNumbers() {
        String username = "user";
        String email = "example@email.com";
        String phoneNumber = "6281123123123";
        String fullname = "Fullname";
        String password = "password";
        String captchaToken = "captcha-token";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterUser(username, email, phoneNumber, fullname, password, captchaToken);
        });

        Assertions.assertEquals("REGISTER_USER.PASSWORD_MUST_CONTAIN_LETTERS_AND_NUMBERS", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error if the password contain space")
    public void shouldThrowErrorIfThePasswordContainSpace() {
        String username = "user";
        String email = "example@email.com";
        String phoneNumber = "6281123123123";
        String fullname = "Fullname";
        String password = "pass word123";
        String captchaToken = "captcha-token";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterUser(username, email, phoneNumber, fullname, password, captchaToken);
        });

        Assertions.assertEquals("REGISTER_USER.PASSWORD_MUST_NOT_CONTAIN_SPACE", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        String username = "user";
        String email = "example@email.com";
        String phoneNumber = "6281123123123";
        String fullname = "Fullname";
        String password = "password1234";
        String captchaToken = "captcha-token";

        RegisterUser registerUser = new RegisterUser(username, email, phoneNumber, fullname, password, captchaToken);

        Assertions.assertEquals(username, registerUser.getUsername());
        Assertions.assertEquals(email, registerUser.getEmail());
        Assertions.assertEquals(phoneNumber, registerUser.getPhoneNumber());
        Assertions.assertEquals(fullname, registerUser.getFullname());
        Assertions.assertEquals(password, registerUser.getPassword());
        Assertions.assertEquals(captchaToken, registerUser.getCaptchaToken());
    }
}
