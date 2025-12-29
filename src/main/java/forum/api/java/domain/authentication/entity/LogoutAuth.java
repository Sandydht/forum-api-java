package forum.api.java.domain.authentication.entity;

public class LogoutAuth {
    private final String refreshToken;

    public LogoutAuth(String refreshToken) {
        verifyPayload(refreshToken);

        this.refreshToken = refreshToken;
    }

    private static void verifyPayload(String refreshToken) {
        requiredNotBlank(refreshToken);
    }

    private static void requiredNotBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("LOGOUT_AUTH.NOT_CONTAIN_NEEDED_PROPERTY");
        }
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
