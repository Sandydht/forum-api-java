package forum.api.java.domain.authentication;

import forum.api.java.domain.user.entity.UserDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@DisplayName("AuthenticationRepository interface")
public class AuthenticationRepositoryTest {
    private final AuthenticationRepository authenticationRepository = new AuthenticationRepository() {};;

    @Test
    @DisplayName("should throw error when invoke unimplemented method")
    public void testThrowErrorWhenInvokeAbstractBehavior() {
        String id = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        String password = "password";
        String refreshToken = "refresh-token";
        Instant expiredDate = Instant.now().plus(Duration.ofDays(7));

        UserDetail loginUser = new UserDetail(id, username, fullname, password);

        UnsupportedOperationException addTokenError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> authenticationRepository.addToken(loginUser, refreshToken, expiredDate)
        );
        Assertions.assertEquals("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED", addTokenError.getMessage());

        UnsupportedOperationException deleteExpiredTokensError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> authenticationRepository.deleteExpiredTokens(Instant.now())
        );
        Assertions.assertEquals("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED", deleteExpiredTokensError.getMessage());

        UnsupportedOperationException checkAvailabilityTokenError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> authenticationRepository.checkAvailabilityToken(null)
        );
        Assertions.assertEquals("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED", checkAvailabilityTokenError.getMessage());
    }
}
