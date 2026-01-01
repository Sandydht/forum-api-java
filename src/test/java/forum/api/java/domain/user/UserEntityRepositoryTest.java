package forum.api.java.domain.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserRepository interface")
public class UserEntityRepositoryTest {
    private final UserRepository userRepository = new UserRepository() {};;

    @Test
    @DisplayName("should throw error when invoke abstract behavior")
    public void testThrowErrorWhenInvokeAbstractBehavior() {
        UnsupportedOperationException addUserError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> userRepository.addUser(null, null, null)
        );
        Assertions.assertEquals("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED", addUserError.getMessage());

        UnsupportedOperationException verifyAvailableUsernameError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> userRepository.verifyAvailableUsername("")
        );
        Assertions.assertEquals("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED", verifyAvailableUsernameError.getMessage());

        UnsupportedOperationException getUserByUsernameError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> userRepository.getUserByUsername("")
        );
        Assertions.assertEquals("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED", getUserByUsernameError.getMessage());

        UnsupportedOperationException getUserByIdError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> userRepository.getUserById("")
        );
        Assertions.assertEquals("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED", getUserByIdError.getMessage());
    }
}
