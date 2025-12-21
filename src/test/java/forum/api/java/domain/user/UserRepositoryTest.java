package forum.api.java.domain.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserRepository interface")
public class UserRepositoryTest {
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepository() {};
    }

    @Test
    @DisplayName("should throw error when invoke abstract behavior")
    public void testThrowErrorWhenInvokeAbstractBehavior() {
        UnsupportedOperationException addUserError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> userRepository.addUser(null)
        );
        Assertions.assertEquals("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED", addUserError.getMessage());

        UnsupportedOperationException verifyAvailableUsernameError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> userRepository.verifyAvailableUsername("")
        );
        Assertions.assertEquals("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED", verifyAvailableUsernameError.getMessage());

        UnsupportedOperationException getPasswordByUsernameError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> userRepository.getPasswordByUsername("")
        );
        Assertions.assertEquals("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED", getPasswordByUsernameError.getMessage());

        UnsupportedOperationException getIdByUsernameError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> userRepository.getIdByUsername("")
        );
        Assertions.assertEquals("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED", getIdByUsernameError.getMessage());
    }
}
