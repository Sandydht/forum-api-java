package forum.api.java.domain.user.entity;

import forum.api.java.domain.utils.ValidationUtils;

public class UserDetail {
    private static final String notContainNeededPropertyErrorMessage = "USER_DETAIL.NOT_CONTAIN_NEEDED_PROPERTY";
    private static final String containRestrictedCharacterErrorMessage = "USER_DETAIL.USERNAME_CONTAIN_RESTRICTED_CHARACTER";
    private static final String limitCharErrorMessage = "USER_DETAIL.USERNAME_LIMIT_CHAR";

    private final String id;
    private final String username;
    private final String fullname;
    private final String password;

    public UserDetail(String id, String username, String fullname, String password) {
        verifyPayload(id, username, fullname, password);

        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
    }

    private static void verifyPayload(String id, String username, String fullname, String password) {
        ValidationUtils.requireNotBlank(id, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(username, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(fullname, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(password, notContainNeededPropertyErrorMessage);
        ValidationUtils.usernameLimitCharacter(username, limitCharErrorMessage);
        ValidationUtils.usernameNotContainRestrictedCharacter(username, containRestrictedCharacterErrorMessage);
    }

    public String getId() {
        return id;
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
}
