package forum.api.java.domain.user.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

@DisplayName("UserDetail entity")
public class UserDetailTest {
    private static final String id = UUID.randomUUID().toString();
    private static final String username = "user";
    private static final String email = "example@email.com";
    private static final String phoneNumber = "6281123123123";
    private static final String fullname = "Fullname";
    private static final String password = "password";

    private static Stream<Arguments> provideInvalidMissingData() {
        return Stream.of(
                Arguments.of(null, username, email, phoneNumber, fullname, password),
                Arguments.of(id, null, email, phoneNumber, fullname, password),
                Arguments.of(id, username, null, phoneNumber, fullname, password),
                Arguments.of(id, username, email, null, fullname, password),
                Arguments.of(id, username, email, phoneNumber, null, password),
                Arguments.of(id, username, email, phoneNumber, fullname, null),
                Arguments.of("  ", username, email, phoneNumber, fullname, password),
                Arguments.of(id, "  ", email, phoneNumber, fullname, password),
                Arguments.of(id, username, "  ", phoneNumber, fullname, password),
                Arguments.of(id, username, email, "  ", fullname, password),
                Arguments.of(id, username, email, phoneNumber, "  ", password),
                Arguments.of(id, username, email, phoneNumber, fullname, "  ")
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String id, String username, String email, String phoneNumber, String fullname, String password) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserDetail(id, username, email, phoneNumber, fullname, password);
        });

        Assertions.assertEquals("USER_DETAIL.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains more than 50 character")
    public void shouldThrowErrorWhenUsernameContainsMoreThan50Character() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserDetail(id, "user".repeat(51), email, phoneNumber, fullname, password);
        });

        Assertions.assertEquals("USER_DETAIL.USERNAME_LIMIT_CHAR", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains restricted character")
    public void shouldThrowErrorWhenUsernameContainsRestrictedCharacter() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserDetail(id, "Invalid Username", email, phoneNumber, fullname, password);
        });

        Assertions.assertEquals("USER_DETAIL.USERNAME_CONTAIN_RESTRICTED_CHARACTER", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when email is invalid")
    public void shouldThrowErrorWhenEmailIsInvalid() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserDetail(id, username, "Invalid Email", phoneNumber, fullname, password);
        });

        Assertions.assertEquals("USER_DETAIL.EMAIL_IS_INVALID", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when phone number is invalid")
    public void shouldThrowErrorWhenPhoneNumberIsInvalid() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserDetail(id, username, email, "Invalid Phone Number", fullname, password);
        });

        Assertions.assertEquals("USER_DETAIL.PHONE_NUMBER_IS_INVALID", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        UserDetail userDetail = new UserDetail(id, username, email, phoneNumber, fullname, password);

        Assertions.assertEquals(id, userDetail.getId());
        Assertions.assertEquals(username, userDetail.getUsername());
        Assertions.assertEquals(email, userDetail.getEmail());
        Assertions.assertEquals(phoneNumber, userDetail.getPhoneNumber());
        Assertions.assertEquals(fullname, userDetail.getFullname());
        Assertions.assertEquals(password, userDetail.getPassword());
    }
}
