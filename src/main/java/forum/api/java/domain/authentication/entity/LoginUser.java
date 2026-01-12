package forum.api.java.domain.authentication.entity;

import forum.api.java.domain.utils.ValidationUtils;

public class LoginUser {
    private static final String notContainNeededPropertyErrorMessage = "LOGIN_USER.NOT_CONTAIN_NEEDED_PROPERTY";
    private static final String containRestrictedCharacterErrorMessage = "LOGIN_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER";
    private static final String limitCharErrorMessage = "LOGIN_USER.USERNAME_LIMIT_CHAR";

    private final String username;
    private final String password;

    public LoginUser(String username, String password) {
        verifyPayload(username, password);

        this.username = username;
        this.password = password;
    }

    private static void verifyPayload(String username, String password) {
        ValidationUtils.requireNotBlank(username, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(password, notContainNeededPropertyErrorMessage);
        ValidationUtils.usernameLimitCharacter(username, limitCharErrorMessage);
        ValidationUtils.usernameNotContainRestrictedCharacter(username, containRestrictedCharacterErrorMessage);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
