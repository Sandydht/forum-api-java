package forum.api.java.domain.authentication.entity;

import forum.api.java.domain.utils.ValidationUtils;

public class LoginUser {
    private static final String notContainNeededPropertyErrorMessage = "LOGIN_USER.NOT_CONTAIN_NEEDED_PROPERTY";
    private static final String containRestrictedCharacterErrorMessage = "LOGIN_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER";
    private static final String limitCharErrorMessage = "LOGIN_USER.USERNAME_LIMIT_CHAR";
    private static final String passwordLimitCharErrorMessage = "LOGIN_USER.PASSWORD_MUST_BE_AT_LEAST_8_CHARACTERS";
    private static final String passwordMustContainLettersAndNumbersErrorMessage = "LOGIN_USER.PASSWORD_MUST_CONTAIN_LETTERS_AND_NUMBERS";
    private static final String passwordMustContainSpaceErrorMessage = "LOGIN_USER.PASSWORD_MUST_NOT_CONTAIN_SPACE";

    private final String username;
    private final String password;
    private final String captchaToken;

    public LoginUser(String username, String password, String captchaToken) {
        verifyPayload(username, password, captchaToken);

        this.username = username;
        this.password = password;
        this.captchaToken = captchaToken;
    }

    private static void verifyPayload(String username, String password, String captchaToken) {
        ValidationUtils.requireNotBlank(username, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(password, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(captchaToken, notContainNeededPropertyErrorMessage);
        ValidationUtils.usernameNotContainRestrictedCharacter(username, containRestrictedCharacterErrorMessage);
        ValidationUtils.usernameLimitCharacter(username, limitCharErrorMessage);
        ValidationUtils.passwordLimitCharacter(password, passwordLimitCharErrorMessage);
        ValidationUtils.passwordMustContainLettersAndNumber(password, passwordMustContainLettersAndNumbersErrorMessage);
        ValidationUtils.passwordMustNotContainSpace(password, passwordMustContainSpaceErrorMessage);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCaptchaToken() {
        return captchaToken;
    }
}
