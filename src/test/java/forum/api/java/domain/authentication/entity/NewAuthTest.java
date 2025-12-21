package forum.api.java.domain.authentication.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("a NewAuth entity")
public class NewAuthTest {
    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @CsvSource({
            ", refreshToken",
            "'', refreshToken",
            "accessToken, ",
            "accessToken, ''"
    })
    public void testNotContainNeededProperty(String accessToken, String refreshToken) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new NewAuth(accessToken, refreshToken);
        });

        Assertions.assertEquals("NEW_AUTH.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create newAuth object correctly")
    public void testObjectCorrectly() {
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        NewAuth newAuth = new NewAuth(accessToken, refreshToken);

        Assertions.assertEquals(accessToken, newAuth.getAccessToken());
        Assertions.assertEquals(refreshToken, newAuth.getRefreshToken());
    }
}
