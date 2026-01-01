package forum.api.java.applications.scheduler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AuthenticationScheduler interface")
public class AuthenticationSchedulerTest {
    private final AuthenticationScheduler authenticationScheduler = new AuthenticationScheduler() {};

    @Test
    @DisplayName("should throw error when invoke unimplemented method")
    public void shouldThrowErrorWhenInvokeUnimplementedMethod() {
        UnsupportedOperationException cleanupExpiredTokenError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                authenticationScheduler::cleanupExpiredToken
        );
        Assertions.assertEquals("AUTHENTICATION_SCHEDULER.METHOD_NOT_IMPLEMENTED", cleanupExpiredTokenError.getMessage());
    }
}
