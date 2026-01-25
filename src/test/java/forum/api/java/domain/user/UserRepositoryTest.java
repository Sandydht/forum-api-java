package forum.api.java.domain.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserRepository interface")
public class UserRepositoryTest {
    private final UserRepository userRepository = new UserRepository() {};

    @Test
    @DisplayName("should throw error when invoke abstract behavior")
    public void shouldThrowErrorWhenInvokeAbstractBehavior() {
        UnsupportedOperationException addUserError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> userRepository.addUser(null)
        );
        Assertions.assertEquals("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED", addUserError.getMessage());

        UnsupportedOperationException verifyAvailableUsernameError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> userRepository.verifyAvailableUsername(null)
        );
        Assertions.assertEquals("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED", verifyAvailableUsernameError.getMessage());

        UnsupportedOperationException getUserByUsernameError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> userRepository.getUserByUsername(null)
        );
        Assertions.assertEquals("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED", getUserByUsernameError.getMessage());

        UnsupportedOperationException getUserByIdError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> userRepository.getUserById(null)
        );
        Assertions.assertEquals("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED", getUserByIdError.getMessage());

        UnsupportedOperationException getUserProfileError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> userRepository.getUserProfile(null)
        );
        Assertions.assertEquals("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED", getUserProfileError.getMessage());

        UnsupportedOperationException checkAvailableUserByIdError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> userRepository.checkAvailableUserById(null)
        );
        Assertions.assertEquals("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED", checkAvailableUserByIdError.getMessage());

        UnsupportedOperationException verifyAvailableEmailError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> userRepository.verifyAvailableEmail(null)
        );
        Assertions.assertEquals("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED", verifyAvailableEmailError.getMessage());

        UnsupportedOperationException verifyAvailablePhoneNumberError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> userRepository.verifyAvailablePhoneNumber(null)
        );
        Assertions.assertEquals("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED", verifyAvailablePhoneNumberError.getMessage());

        UnsupportedOperationException getUserByEmailForgotPasswordError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> userRepository.getUserByEmailForgotPassword(null)
        );
        Assertions.assertEquals("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED", getUserByEmailForgotPasswordError.getMessage());
    }
}
