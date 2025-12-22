package forum.api.java.domain.authentication;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AuthenticationRepository interface")
public class AuthenticationRepositoryTest {
    private AuthenticationRepository authenticationRepository = new AuthenticationRepository() {};;

    @Test
    @DisplayName("should throw error when invoke unimplemented method")
    public void testThrowErrorWhenInvokeAbstractBehavior() {
        UnsupportedOperationException addTokenError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> authenticationRepository.addToken("")
        );
        Assertions.assertEquals("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED", addTokenError.getMessage());
    }
}
