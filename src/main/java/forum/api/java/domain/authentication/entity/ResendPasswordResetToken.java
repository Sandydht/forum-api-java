package forum.api.java.domain.authentication.entity;

import forum.api.java.domain.utils.ValidationUtils;

public class ResendPasswordResetToken {
    private static final String notContainNeededPropertyErrorMessage = "RESEND_PASSWORD_RESET_TOKEN.NOT_CONTAIN_NEEDED_PROPERTY";
    private static final String ipInvalidErrorMessage = "RESEND_PASSWORD_RESET_TOKEN.IP_ADDRESS_IS_INVALID";

    private final String rawToken;
    private final String ipRequest;
    private final String userAgent;

    public ResendPasswordResetToken(String rawToken, String ipRequest, String userAgent) {
        verifyPayload(rawToken, ipRequest, userAgent);

        this.rawToken = rawToken;
        this.ipRequest = ipRequest;
        this.userAgent = userAgent;
    }

    private static void verifyPayload(String rawToken, String ipRequest, String userAgent) {
        ValidationUtils.requireNotBlank(rawToken, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(ipRequest, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(userAgent, notContainNeededPropertyErrorMessage);

        ValidationUtils.validateIpAddress(ipRequest, ipInvalidErrorMessage);
    }

    public String getRawToken() {
        return rawToken;
    }

    public String getIpRequest() {
        return ipRequest;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
