package forum.api.java.commons.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DomainErrorTranslator")
public class DomainErrorTranslatorTest {
    private final DomainErrorTranslator domainErrorTranslator = new DomainErrorTranslator();

    @Test
    @DisplayName("should translate REGISTER_USER.NOT_CONTAIN_NEEDED_PROPERTY correctly")
    public void shouldTranslateRegisterUserMissingProperty() {
        Exception error = new Exception("REGISTER_USER.NOT_CONTAIN_NEEDED_PROPERTY");
        RuntimeException result = domainErrorTranslator.translate(error);

        Assertions.assertInstanceOf(InvariantException.class, result);
        Assertions.assertEquals("Cannot create new user because required property is missing", result.getMessage());
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
    @DisplayName("should translate REGISTER_USER.NOT_MEET_DATA_TYPE_SPECIFICATION correctly")
    public void shouldTranslateRegisterUserUsernameNotMeetDataTypeSpesification() {
        Exception error = new Exception("REGISTER_USER.NOT_MEET_DATA_TYPE_SPECIFICATION");
        RuntimeException result = domainErrorTranslator.translate(error);

        Assertions.assertInstanceOf(InvariantException.class, result);
        Assertions.assertEquals("Cannot create new user because data type does not match", result.getMessage());
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
    @DisplayName("should translate USER_LOGIN.NOT_CONTAIN_NEEDED_PROPERTY correctly")
    public void shouldTranslateUserLoginNotContainNeededProperty() {
        Exception error = new Exception("USER_LOGIN.NOT_CONTAIN_NEEDED_PROPERTY");
        RuntimeException result = domainErrorTranslator.translate(error);

        Assertions.assertInstanceOf(InvariantException.class, result);
        Assertions.assertEquals("Must send username and password", result.getMessage());
    }

    @Test
    @DisplayName("should translate USER_LOGIN.USERNAME_LIMIT_CHAR correctly")
    public void shouldTranslateUserLoginUsernameLimit() {
        Exception error = new Exception("USER_LOGIN.USERNAME_LIMIT_CHAR");
        RuntimeException result = domainErrorTranslator.translate(error);

        Assertions.assertInstanceOf(InvariantException.class, result);
        Assertions.assertEquals("Username character exceeds the limit", result.getMessage());
    }

    @Test
    @DisplayName("should translate USER_LOGIN.USERNAME_CONTAIN_RESTRICTED_CHARACTER correctly")
    public void shouldTranslateUserLoginUsernameContainRestrictedCharacter() {
        Exception error = new Exception("USER_LOGIN.USERNAME_CONTAIN_RESTRICTED_CHARACTER");
        RuntimeException result = domainErrorTranslator.translate(error);

        Assertions.assertInstanceOf(InvariantException.class, result);
        Assertions.assertEquals("Username contains prohibited characters", result.getMessage());
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
