package forum.api.java.domain.authentication.entity;

public class NewAuth {
    private final String accessToken;
    private final String refreshToken;

    public NewAuth(String accessToken, String refreshToken) {
        verifyPayload(accessToken, refreshToken);

        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    private void verifyPayload(String accessToken, String refreshToken) {
        if (accessToken == null || accessToken.isBlank() || refreshToken == null || refreshToken.isBlank()) {
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
