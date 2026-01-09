package forum.api.java.domain.user.entity;

import forum.api.java.domain.utils.ValidationUtils;

public class RegisteredUser {
    private static final String notContainNeededPropertyErrorMessage = "REGISTERED_USER.NOT_CONTAIN_NEEDED_PROPERTY";
    private static final String containRestrictedCharacterErrorMessage = "REGISTERED_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER";
    private static final String limitCharErrorMessage = "REGISTERED_USER.USERNAME_LIMIT_CHAR";

    private final String id;
    private final String username;
    private final String fullname;

    public RegisteredUser(String id, String username, String fullname) {
        verifyPayload(id, username, fullname);

        this.id = id;
        this.username = username;
        this.fullname = fullname;
    }

    private static void verifyPayload(String id, String username, String fullname) {
        ValidationUtils.requireNotBlank(id, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(username, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(fullname, notContainNeededPropertyErrorMessage);
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
}
