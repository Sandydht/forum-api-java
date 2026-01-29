package forum.api.java.commons.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("DomainErrorTranslator")
public class DomainErrorTranslatorTest {
    private final DomainErrorTranslator domainErrorTranslator = new DomainErrorTranslator();

    @Nested
    @DisplayName("REGISTER_USER")
    public class RegisterUserErrorMessages {
        @Test
        @DisplayName("should translate REGISTER_USER.NOT_CONTAIN_NEEDED_PROPERTY correctly")
        public void shouldTranslateRegisterUserMissingProperty() {
            Exception error = new Exception("REGISTER_USER.NOT_CONTAIN_NEEDED_PROPERTY");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("Cannot create new user because required property is missing", result.getMessage());
        }

        @Test
        @DisplayName("should translate REGISTER_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER correctly")
        public void shouldTranslateRegisterUserUsernameContainRestrictedCharacter() {
            Exception error = new Exception("REGISTER_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("Cannot create a new user because the username contains prohibited characters", result.getMessage());
        }

        @Test
        @DisplayName("should translate REGISTER_USER.USERNAME_LIMIT_CHAR correctly")
        public void shouldTranslateRegisterUserUsernameLimit() {
            Exception error = new Exception("REGISTER_USER.USERNAME_LIMIT_CHAR");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("Cannot create a new user because the username character exceeds the limit", result.getMessage());
        }

        @Test
        @DisplayName("should translate REGISTER_USER.EMAIL_IS_INVALID correctly")
        public void shouldTranslateRegisterUserEmailIsInvalid() {
            Exception error = new Exception("REGISTER_USER.EMAIL_IS_INVALID");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("Cannot create a new user because the email is invalid", result.getMessage());
        }

        @Test
        @DisplayName("should translate REGISTER_USER.PHONE_NUMBER_IS_INVALID correctly")
        public void shouldTranslateRegisterUserPhoneNumberIsInvalid() {
            Exception error = new Exception("REGISTER_USER.PHONE_NUMBER_IS_INVALID");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("Cannot create a new user because the phone number is invalid", result.getMessage());
        }

        @Test
        @DisplayName("should translate REGISTER_USER.PASSWORD_MUST_BE_AT_LEAST_8_CHARACTERS correctly")
        public void shouldTranslateRegisterUserMustBeAtLeast8Characters() {
            Exception error = new Exception("REGISTER_USER.PASSWORD_MUST_BE_AT_LEAST_8_CHARACTERS");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("Cannot create a new user because the password less than 8 characters", result.getMessage());
        }

        @Test
        @DisplayName("should translate REGISTER_USER.PASSWORD_MUST_CONTAIN_LETTERS_AND_NUMBERS correctly")
        public void shouldTranslateRegisterUserPasswordMustContainLettersAndNumbers() {
            Exception error = new Exception("REGISTER_USER.PASSWORD_MUST_CONTAIN_LETTERS_AND_NUMBERS");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("Cannot create a new user because the password not contain letters and numbers", result.getMessage());
        }

        @Test
        @DisplayName("should translate REGISTER_USER.PASSWORD_MUST_NOT_CONTAIN_SPACE correctly")
        public void shouldTranslateRegisterUserPasswordMusNotContainSpace() {
            Exception error = new Exception("REGISTER_USER.PASSWORD_MUST_NOT_CONTAIN_SPACE");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("Cannot create a new user because the password contain space", result.getMessage());
        }
    }

    @Nested
    @DisplayName("LOGIN_USER")
    public class UserLoginErrorMessages {
        @Test
        @DisplayName("should translate LOGIN_USER.NOT_CONTAIN_NEEDED_PROPERTY correctly")
        public void shouldTranslateLoginUserNotContainNeededProperty() {
            Exception error = new Exception("LOGIN_USER.NOT_CONTAIN_NEEDED_PROPERTY");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("Must send username and password", result.getMessage());
        }

        @Test
        @DisplayName("should translate LOGIN_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER correctly")
        public void shouldTranslateLoginUserUsernameContainRestrictedCharacter() {
            Exception error = new Exception("LOGIN_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("Username contains prohibited characters", result.getMessage());
        }

        @Test
        @DisplayName("should translate LOGIN_USER.USERNAME_LIMIT_CHAR correctly")
        public void shouldTranslateLoginUserUsernameLimit() {
            Exception error = new Exception("LOGIN_USER.USERNAME_LIMIT_CHAR");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("Username character exceeds the limit", result.getMessage());
        }

        @Test
        @DisplayName("should translate LOGIN_USER.PASSWORD_MUST_BE_AT_LEAST_8_CHARACTERS correctly")
        public void shouldTranslateLoginUserPasswordMustBeAtLeast8Characters() {
            Exception error = new Exception("LOGIN_USER.PASSWORD_MUST_BE_AT_LEAST_8_CHARACTERS");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("Password less than 8 characters", result.getMessage());
        }

        @Test
        @DisplayName("should translate LOGIN_USER.PASSWORD_MUST_CONTAIN_LETTERS_AND_NUMBERS correctly")
        public void shouldTranslateLoginUserPasswordMustContainLettersAndNumbers() {
            Exception error = new Exception("LOGIN_USER.PASSWORD_MUST_CONTAIN_LETTERS_AND_NUMBERS");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("Password not contain letters and numbers", result.getMessage());
        }

        @Test
        @DisplayName("should translate LOGIN_USER.PASSWORD_MUST_NOT_CONTAIN_SPACE correctly")
        public void shouldTranslateLoginUserPasswordMustNotContainSpace() {
            Exception error = new Exception("LOGIN_USER.PASSWORD_MUST_NOT_CONTAIN_SPACE");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("Password contain space", result.getMessage());
        }
    }

    @Nested
    @DisplayName("PASSWORD_HASH_IMPL")
    public class PasswordHashImplErrorMessages {
        @Test
        @DisplayName("should translate PASSWORD_HASH_IMPL.INCORRECT_CREDENTIALS correctly")
        public void shouldTranslatePasswordHashImplIncorrectCredentials() {
            Exception error = new Exception("PASSWORD_HASH_IMPL.INCORRECT_CREDENTIALS");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(AuthenticationException.class, result);
            Assertions.assertEquals("Incorrect credentials", result.getMessage());
        }
    }

    @Nested
    @DisplayName("USER_REPOSITORY_IMPL")
    public class UserRepositoryImplErrorMessages {
        @Test
        @DisplayName("should translate USER_REPOSITORY_IMPL.USER_ALREADY_EXIST correctly")
        public void shouldTranslateUserRepositoryImplUserAlreadyExistCorrectly() {
            Exception error = new Exception("USER_REPOSITORY_IMPL.USER_ALREADY_EXIST");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("User already exist", result.getMessage());
        }

        @Test
        @DisplayName("should translate USER_REPOSITORY_IMPL.USER_NOT_FOUND correctly")
        public void shouldTranslateUserRepositoryImplUserNotFoundCorrectly() {
            Exception error = new Exception("USER_REPOSITORY_IMPL.USER_NOT_FOUND");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(NotFoundException.class, result);
            Assertions.assertEquals("User not found", result.getMessage());
        }
    }

    @Nested
    @DisplayName("AUTHENTICATION_REPOSITORY_IMPL")
    public class AuthenticationRepositoryImplErrorMessages {
        @Test
        @DisplayName("should translate AUTHENTICATION_REPOSITORY_IMPL.USER_NOT_FOUND correctly")
        public void shouldTranslateAuthenticationRepositoryImplUserNotFoundCorrectly() {
            Exception error = new Exception("AUTHENTICATION_REPOSITORY_IMPL.USER_NOT_FOUND");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(NotFoundException.class, result);
            Assertions.assertEquals("User not found", result.getMessage());
        }

        @Test
        @DisplayName("should translate AUTHENTICATION_REPOSITORY_IMPL.TOKEN_NOT_FOUND correctly")
        public void shouldTranslateAuthenticationRepositoryImplTokenNotFoundCorrectly() {
            Exception error = new Exception("AUTHENTICATION_REPOSITORY_IMPL.TOKEN_NOT_FOUND");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(NotFoundException.class, result);
            Assertions.assertEquals("Token not found", result.getMessage());
        }

        @Test
        @DisplayName("should translate AUTHENTICATION_REPOSITORY_IMPL.INVALID_OR_EXPIRED_PASSWORD_RESET_TOKEN correctly")
        public void shouldTranslateAuthenticationRepositoryImplInvalidOrExpiredPasswordResetTokenCorrectly() {
            Exception error = new Exception("AUTHENTICATION_REPOSITORY_IMPL.INVALID_OR_EXPIRED_PASSWORD_RESET_TOKEN");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("This password reset link is invalid or has expired. Please request a new one", result.getMessage());
        }
    }

    @Nested
    @DisplayName("THREAD_REPOSITORY_IMPL")
    public class ThreadRepositoryImplErrorMessages {
        @Test
        @DisplayName("should translate THREAD_REPOSITORY_IMPL.USER_NOT_FOUND correctly")
        public void shouldTranslateThreadRepositoryImplUserNotFoundCorrectly() {
            Exception error = new Exception("THREAD_REPOSITORY_IMPL.USER_NOT_FOUND");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(NotFoundException.class, result);
            Assertions.assertEquals("User not found", result.getMessage());
        }

        @Test
        @DisplayName("should translate THREAD_REPOSITORY_IMPL.THREAD_NOT_FOUND correctly")
        public void shouldTranslateThreadRepositoryImplThreadNotFoundCorrectly() {
            Exception error = new Exception("THREAD_REPOSITORY_IMPL.THREAD_NOT_FOUND");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(NotFoundException.class, result);
            Assertions.assertEquals("Thread not found", result.getMessage());
        }
    }

    @Nested
    @DisplayName("REQUEST_RESET_PASSWORD_LINK")
    public class RequestResetPasswordLinkErrorMessages {
        @Test
        @DisplayName("should translate REQUEST_RESET_PASSWORD_LINK.EMAIL_IS_INVALID correctly")
        public void shouldTranslateRequestResetPasswordLinkEmailIsInvalidCorrectly() {
            Exception error = new Exception("REQUEST_RESET_PASSWORD_LINK.EMAIL_IS_INVALID");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("Email is invalid", result.getMessage());
        }

        @Test
        @DisplayName("should translate REQUEST_RESET_PASSWORD_LINK.IP_ADDRESS_IS_INVALID correctly")
        public void shouldTranslateRequestResetPasswordLinkIpAddressIsInvalidCorrectly() {
            Exception error = new Exception("REQUEST_RESET_PASSWORD_LINK.IP_ADDRESS_IS_INVALID");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("IP address is invalid", result.getMessage());
        }
    }

    @Nested
    @DisplayName("RESEND_PASSWORD_RESET_TOKEN")
    public class ResendPasswordResetTokenErrorMessages {
        @Test
        @DisplayName("should translate RESEND_PASSWORD_RESET_TOKEN.IP_ADDRESS_IS_INVALID correctly")
        public void shouldTranslateResendPasswordResetTokenIpAddressIsInvalidCorrectly() {
            Exception error = new Exception("RESEND_PASSWORD_RESET_TOKEN.IP_ADDRESS_IS_INVALID");
            RuntimeException result = domainErrorTranslator.translate(error);

            Assertions.assertInstanceOf(InvariantException.class, result);
            Assertions.assertEquals("IP address is invalid", result.getMessage());
        }
    }

    @Test
    @DisplayName("should return original exception when error message is not in directories")
    public void shouldReturnOriginalWhenNotMapped() {
        String unknownMessage = "SOME_UNKNOWN_MESSAGE";
        RuntimeException originalError = new RuntimeException(unknownMessage);

        RuntimeException result = domainErrorTranslator.translate(originalError);

        Assertions.assertEquals(originalError, result);
        Assertions.assertEquals(unknownMessage, result.getMessage());
    }

    @Test
    @DisplayName("should wrap checked Exception into RuntimeException if not mapped")
    public void shouldWrapCheckedException() {
        Exception checkedError = new Exception("DATABASE_CONNECTION_ERROR");

        RuntimeException result = domainErrorTranslator.translate(checkedError);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result instanceof InvariantException);
        Assertions.assertEquals("DATABASE_CONNECTION_ERROR", result.getMessage());
    }
}
