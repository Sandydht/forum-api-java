package forum.api.java.domain.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Domain validation utils")
public class ValidationUtilsTest {
    @Nested
    @DisplayName("requireNotBlank function")
    public class RequireNotBlankFunction {
        @Test
        @DisplayName("should throw IllegalArgumentException when value is null")
        public void shouldThrowIllegalArgumentExceptionWhenValueIsNull() {
            String errorMessage = "VALUE_IS_NULL";

            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> ValidationUtils.requireNotBlank(null, errorMessage)
            );

            Assertions.assertEquals(errorMessage, exception.getMessage());
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "  ", "      \n"})
        @DisplayName("should throw IllegalArgumentException when value is empty or only whitespace")
        public void shouldThrowIllegalArgumentExceptionWhenValueIsEmptyOrOnlyWhitespace(String value) {
            String errorMessage = "VALUE_IS_BLANK";

            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> ValidationUtils.requireNotBlank(value, errorMessage)
            );

            Assertions.assertEquals(errorMessage, exception.getMessage());
        }

        @Test
        @DisplayName("should not throw exception when value is valid")
        public void shouldNotThrowExceptionWhenValueIsValid() {
            Assertions.assertDoesNotThrow(() -> ValidationUtils.requireNotBlank("valid_value", "error"));
        }
    }

    @Nested
    @DisplayName("usernameLimitCharacter function")
    public class UsernameLimitCharacterFunction {
        @Test
        @DisplayName("should throw IllegalArgumentException when username contains more than 50 character")
        public void shouldThrowIllegalArgumentExceptionWhenUsernameContainsMoreThan50Character() {
            String username = "a".repeat(51);
            String errorMessage = "USERNAME_TOO_LONG";

            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> ValidationUtils.usernameLimitCharacter(username, errorMessage)
            );

            Assertions.assertEquals(errorMessage, exception.getMessage());
        }

        @Test
        @DisplayName("should not throw exception when username is exactly 50 character")
        public void shouldNotThrowExceptionWhenUsernameIsExactly50Character() {
            String username = "a".repeat(50);
            String errorMessage = "USERNAME_TOO_LONG";

            Assertions.assertDoesNotThrow(() -> ValidationUtils.usernameLimitCharacter(username, errorMessage));
        }
    }

    @Nested
    @DisplayName("usernameNotContainRestrictedCharacter function")
    public class UsernameNotContainRestrictedCharacterFunction {
        @ParameterizedTest
        @ValueSource(strings = {"user name", "user-name", "user!", "@user", "user#", "user.name"})
        @DisplayName("should throw IllegalArgumentException when username contains restricted character")
        public void shouldThrowIllegalArgumentExceptionWhenUsernameContainsRestrictedCharacter(String username) {
            String errorMessage = "RESTRICTRED_CHARACTER";

            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> ValidationUtils.usernameNotContainRestrictedCharacter(username, errorMessage)
            );

            Assertions.assertEquals(errorMessage, exception.getMessage());
        }

        @ParameterizedTest
        @ValueSource(strings = {"username", "user_123", "USER_NAME", "12345"})
        @DisplayName("should not throw exception when username is valid")
        public void shouldNotThrowExceptionWhenUsernameIsValid(String username) {
            String errorMessage = "RESTRICTRED_CHARACTER";

            Assertions.assertDoesNotThrow(() -> ValidationUtils.usernameNotContainRestrictedCharacter(username, errorMessage));
        }
    }

    @Nested
    @DisplayName("requireNonNull function")
    public class RequireNonNullFunction {
        @Test
        @DisplayName("should throw IllegalArgumentException when object is null")
        public void shouldThrowIllegalArgumentExceptionWhenObjectIsNull() {
            Object input = null;
            String errorMessage = "OBJECT_SHOULD_NOT_BE_NULL";

            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> ValidationUtils.requireNonNull(input, errorMessage)
            );

            Assertions.assertEquals(errorMessage, exception.getMessage());
        }

        @Test
        @DisplayName("should not throw exception when object is valid")
        public void shouldNotThrowExceptionWhenObjectIsValid() {
            Object input = new Object();
            String errorMessage = "OBJECT_SHOULD_NOT_BE_NULL";

            Assertions.assertDoesNotThrow(() -> ValidationUtils.requireNonNull(input, errorMessage));
        }
    }

    @Nested
    @DisplayName("passwordLimitCharacter function")
    public class PasswordLimitCharacterFunction {
        @Test
        @DisplayName("should throw IllegalArgumentException if the password less than 8 characters")
        public void shouldThrowIllegalArgumentExceptionIfThePasswordLessThan8Characters() {
            String password = "secret";
            String errorMessage = "PASSWORD_MUST_BE_AT_LEAST_8_CHARACTERS";

            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> ValidationUtils.passwordLimitCharacter(password, errorMessage)
            );

            Assertions.assertEquals(errorMessage, exception.getMessage());
        }

        @Test
        @DisplayName("should not throw IllegalArgumentException if the password more than 8 characters")
        public void shouldNotThrowIllegalArgumentExceptionIfThePasswordMoreThan8Characters() {
            String password = "secret".repeat(10);
            String errorMessage = "PASSWORD_MUST_BE_AT_LEAST_8_CHARACTERS";

            Assertions.assertDoesNotThrow(() -> ValidationUtils.passwordLimitCharacter(password, errorMessage));
        }
    }

    @Nested
    @DisplayName("passwordMustContainLettersAndNumber function")
    public class PasswordMustContainLettersAndNumberFunction {
        @Test
        @DisplayName("should throw IllegalArgumentException if the password not contain letters and numbers")
        public void shouldThrowIllegalArgumentExceptionIfThePasswordNotContainLettersAndNumbers() {
            String password = "password";
            String errorMessage = "PASSWORD_MUST_CONTAIN_LETTERS_AND_NUMBERS";

            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> ValidationUtils.passwordMustContainLettersAndNumber(password, errorMessage)
            );

            Assertions.assertEquals(errorMessage, exception.getMessage());
        }

        @Test
        @DisplayName("should not throw IllegalArgumentException if the password contain letters and numbers")
        public void shouldNotThrowIllegalArgumentExceptionIfThePasswordContainLettersAndNumbers() {
            String password = "password123";
            String errorMessage = "PASSWORD_MUST_CONTAIN_LETTERS_AND_NUMBERS";

            Assertions.assertDoesNotThrow(() -> ValidationUtils.passwordMustContainLettersAndNumber(password, errorMessage));
        }
    }

    @Nested
    @DisplayName("passwordMustNotContainSpace function")
    public class PasswordMustNotContainSpaceFunction {
        @Test
        @DisplayName("should throw IllegalArgumentException if the password contain space")
        public void shouldThrowIllegalArgumentExceptionIfThePasswordContainSpace() {
            String password = "password 123";
            String errorMessage = "PASSWORD_MUST_NOT_CONTAIN_SPACE";

            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> ValidationUtils.passwordMustNotContainSpace(password, errorMessage)
            );

            Assertions.assertEquals(errorMessage, exception.getMessage());
        }

        @Test
        @DisplayName("should not throw IllegalArgumentException if the password not contain space")
        public void shouldNotThrowIllegalArgumentExceptionIfThePasswordNotContainSpace() {
            String password = "password123";
            String errorMessage = "PASSWORD_MUST_NOT_CONTAIN_SPACE";

            Assertions.assertDoesNotThrow(() -> ValidationUtils.passwordMustNotContainSpace(password, errorMessage));
        }
    }

    @Nested
    @DisplayName("emailValidation function")
    public class EmailValidationFunction {
        @Test
        @DisplayName("should throw IllegalArgumentException if the email is invalid")
        public void shouldThrowIllegalArgumentExceptionIfTheEmailIsInvalid() {
            String email = "Invalid Email";
            String errorMessage = "EMAIL_IS_INVALID";

            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> ValidationUtils.emailValidation(email, errorMessage)
            );

            Assertions.assertEquals(errorMessage, exception.getMessage());
        }

        @Test
        @DisplayName("should not throw IllegalArgumentException if the email is valid")
        public void shouldNotThrowIllegalArgumentExceptionIfTheEmailIsValid() {
            String email = "example@email.com";
            String errorMessage = "EMAIL_IS_INVALID";

            Assertions.assertDoesNotThrow(() -> ValidationUtils.emailValidation(email, errorMessage));
        }
    }

    @Nested
    @DisplayName("phoneNumberValidation function")
    public class PhoneNumberValidationFunction {
        @Test
        @DisplayName("should throw IllegalArgumentException if the phone number is invalid")
        public void shouldThrowIllegalArgumentExceptionIfThePhoneNumberIsInvalid() {
            String phoneNumber = "Invalid Phone Number";
            String errorMessage = "PHONE_NUMBER_IS_INVALID";

            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> ValidationUtils.phoneNumberValidation(phoneNumber, errorMessage)
            );

            Assertions.assertEquals(errorMessage, exception.getMessage());
        }

        @Test
        @DisplayName("should not throw IllegalArgumentException if the phone number is valid")
        public void shouldNotThrowIllegalArgumentExceptionIfThePhoneNumberIsValid() {
            String phoneNumber = "081123123123";
            String errorMessage = "PHONE_NUMBER_IS_INVALID";

            Assertions.assertDoesNotThrow(() -> ValidationUtils.phoneNumberValidation(phoneNumber, errorMessage));
        }
    }

    @Nested
    @DisplayName("ipv4Validation function")
    public class Ipv4ValidationFunction {
        @Test
        @DisplayName("should throw IllegalArgumentException if the ip request is invalid")
        public void shouldThrowIllegalArgumentExceptionIfTheIpRequestIsInvalid() {
            String ipRequest = "Invalid IP Request";
            String errorMessage = "IP_REQUEST_IS_INVALID";

            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> ValidationUtils.ipv4Validation(ipRequest, errorMessage)
            );

            Assertions.assertEquals(errorMessage, exception.getMessage());
        }

        @Test
        @DisplayName("should not throw IllegalArgumentException if the ip request is valid")
        public void shouldNotThrowIllegalArgumentExceptionIfTheIpRequestIsValid() {
            String ipRequest = "192.168.1.1";
            String errorMessage = "IP_REQUEST_IS_INVALID";

            Assertions.assertDoesNotThrow(() -> ValidationUtils.ipv4Validation(ipRequest, errorMessage));
        }
    }
}
