package forum.api.java.domain.user.entity;

import forum.api.java.domain.utils.ValidationUtils;

public class RegisterUser {
    private static final String notContainNeededPropertyErrorMessage = "REGISTER_USER.NOT_CONTAIN_NEEDED_PROPERTY";
    private static final String containRestrictedCharacterErrorMessage = "REGISTER_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER";
    private static final String limitCharErrorMessage = "REGISTER_USER.USERNAME_LIMIT_CHAR";

    private final String username;
    private final String fullname;
    private String password;

    public RegisterUser(String username, String fullname, String password) {
        verifyPayload(username, fullname, password);

        this.username = username;
        this.fullname = fullname;
        this.password = password;
    }

    private static void verifyPayload(String username, String fullname, String password) {
        ValidationUtils.requireNotBlank(username, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(fullname, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(password, notContainNeededPropertyErrorMessage);
        ValidationUtils.usernameLimitCharacter(username, limitCharErrorMessage);
        ValidationUtils.usernameNotContainRestrictedCharacter(username, containRestrictedCharacterErrorMessage);
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String hashedPassword) {
        this.password = hashedPassword;
    }
}
