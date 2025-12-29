package forum.api.java.domain.authentication.entity;

public class NewAuth {
    private final String accessToken;
    private final String refreshToken;

    public NewAuth(String accessToken, String refreshToken) {
        verifyPayload(accessToken, refreshToken);

        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    private static void verifyPayload(String accessToken, String refreshToken) {
        requireNotBlank(accessToken);
        requireNotBlank(refreshToken);
    }

    private static void requireNotBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("NEW_AUTH.NOT_CONTAIN_NEEDED_PROPERTY");
        }
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
