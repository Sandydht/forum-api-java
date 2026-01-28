package forum.api.java.domain.authentication;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AuthenticationRepository interface")
public class AuthenticationRepositoryTest {
    private final AuthenticationRepository authenticationRepository = new AuthenticationRepository() {};;

    @Test
    @DisplayName("should throw error when invoke unimplemented method")
    public void shouldThrowErrorWhenInvokeUnimplementedMethod() {
        UnsupportedOperationException addTokenError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> authenticationRepository.addToken(null, null)
        );
        Assertions.assertEquals("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED", addTokenError.getMessage());

        UnsupportedOperationException deleteExpiredTokensError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> authenticationRepository.deleteExpiredTokens(null)
        );
        Assertions.assertEquals("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED", deleteExpiredTokensError.getMessage());

        UnsupportedOperationException checkAvailabilityTokenError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> authenticationRepository.checkAvailabilityToken(null)
        );
        Assertions.assertEquals("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED", checkAvailabilityTokenError.getMessage());

        UnsupportedOperationException deleteTokenError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> authenticationRepository.deleteToken(null)
        );
        Assertions.assertEquals("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED", deleteTokenError.getMessage());

        UnsupportedOperationException deleteTokenByUserIdError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> authenticationRepository.deleteTokenByUserId(null)
        );
        Assertions.assertEquals("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED", deleteTokenByUserIdError.getMessage());

        UnsupportedOperationException addPasswordResetTokenError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> authenticationRepository.addPasswordResetToken(null, null, null, null)
        );
        Assertions.assertEquals("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED", addPasswordResetTokenError.getMessage());
    }
}
