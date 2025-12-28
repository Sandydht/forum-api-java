package forum.api.java.domain.authentication.entity;

public class RefreshAuth {
    private final String refreshToken;

    public RefreshAuth(String refreshToken) {
        verifyPayload(refreshToken);

        this.refreshToken = refreshToken;
    }

    private void verifyPayload(String refreshToken) {
        requireNotBlank(refreshToken);
    }

    private static void requireNotBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("REFRESH_AUTH.NOT_CONTAIN_NEEDED_PROPERTY");
        }
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
