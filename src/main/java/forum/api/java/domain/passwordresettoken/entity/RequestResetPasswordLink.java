package forum.api.java.domain.passwordresettoken.entity;

import forum.api.java.domain.utils.ValidationUtils;

public class RequestResetPasswordLink {
    private static final String notContainNeededPropertyErrorMessage = "REQUEST_RESET_PASSWORD_LINK.NOT_CONTAIN_NEEDED_PROPERTY";
    private static final String emailInvalidErrorMessage = "REQUEST_RESET_PASSWORD_LINK.EMAIL_IS_INVALID";

    private final String email;
    private final String ipRequest;
    private final String userAgent;
    private final String captchaToken;

    public RequestResetPasswordLink(String email, String ipRequest, String userAgent, String captchaToken) {
        verifyPayload(email, ipRequest, userAgent, captchaToken);

        this.email = email;
        this.ipRequest = ipRequest;
        this.userAgent = userAgent;
        this.captchaToken = captchaToken;
    }

    private static void verifyPayload(String email, String ipRequest, String userAgent, String captchaToken) {
        ValidationUtils.requireNotBlank(email, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(ipRequest, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(userAgent, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(captchaToken, notContainNeededPropertyErrorMessage);
        ValidationUtils.emailValidation(email, emailInvalidErrorMessage);
    }

    public String getEmail() {
        return email;
    }

    public String getIpRequest() {
        return ipRequest;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getCaptchaToken() {
        return captchaToken;
    }
}
