package forum.api.java.domain.authentication.entity;

import forum.api.java.domain.utils.ValidationUtils;

public class NewAuthentication {
    private static final String notContainNeededPropertyErrorMessage = "NEW_AUTHENTICATION.NOT_CONTAIN_NEEDED_PROPERTY";

    private final String accessToken;
    private final String refreshToken;

    public NewAuthentication(String accessToken, String refreshToken) {
        verifyPayload(accessToken, refreshToken);

        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    private static void verifyPayload(String accessToken, String refreshToken) {
        ValidationUtils.requireNotBlank(accessToken, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(refreshToken, notContainNeededPropertyErrorMessage);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
