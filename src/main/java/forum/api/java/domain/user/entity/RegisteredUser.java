package forum.api.java.domain.user.entity;

import java.util.regex.Pattern;

public class RegisteredUser {
    private final String id;
    private final String username;
    private final String fullname;

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[\\w]+$");

    public RegisteredUser(String id, String username, String fullname) {
        verifyPayload(id, username, fullname);

        this.id = id;
        this.username = username;
        this.fullname = fullname;
    }

    private static void verifyPayload(String id, String username, String fullname) {
        requireNotBlank(id);
        requireNotBlank(username);
        requireNotBlank(fullname);

        if (username.length() > 50) {
            throw new IllegalArgumentException("REGISTERED_USER.USERNAME_LIMIT_CHAR");
        }

        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new IllegalArgumentException("REGISTERED_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER");
        }
    }

    private static void requireNotBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("REGISTERED_USER.NOT_CONTAIN_NEEDED_PROPERTY");
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
}
