package forum.api.java.domain.user.entity;

import forum.api.java.domain.utils.ValidationUtils;

public class UserProfile {
    private static final String notContainNeededPropertyErrorMessage = "USER_PROFILE.NOT_CONTAIN_NEEDED_PROPERTY";
    private static final String containRestrictedCharacterErrorMessage = "USER_PROFILE.USERNAME_CONTAIN_RESTRICTED_CHARACTER";
    private static final String limitCharErrorMessage = "USER_PROFILE.USERNAME_LIMIT_CHAR";

    private final String id;
    private final String username;
    private final String fullname;

    public UserProfile(String id, String username, String fullname) {
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
