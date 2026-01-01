package forum.api.java.domain.user.entity;

import java.util.regex.Pattern;

public class UserEntity {
    private final String id;
    private final String username;
    private final String fullname;
    private final String password;
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[\\w]+$");

    public UserEntity(String id, String username, String fullname, String password) {
        verifyPayload(id, username, fullname, password);

        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
    }

    private static void verifyPayload(String id, String username, String fullname, String password) {
        requireNotBlank(id);
        requireNotBlank(username);
        requireNotBlank(fullname);
        requireNotBlank(password);
        usernameLimitCharacter(username);
        usernameNotContainRestrictedCharacter(username);
    }

    private static void requireNotBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("USER.NOT_CONTAIN_NEEDED_PROPERTY");
        }
    }

    private static void usernameLimitCharacter(String username) {
        if (username.length() > 50) {
            throw new IllegalArgumentException("USER.USERNAME_LIMIT_CHAR");
        }
    }

    private static void usernameNotContainRestrictedCharacter(String username) {
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new IllegalArgumentException("USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER");
        }
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
