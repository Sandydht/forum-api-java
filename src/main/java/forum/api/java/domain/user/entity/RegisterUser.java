package forum.api.java.domain.user.entity;

import java.util.UUID;
import java.util.regex.Pattern;

public class RegisterUser {
    private final UUID id;
    private final String username;
    private final String fullname;
    private String password;

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[\\w]+$");

    public RegisterUser(String username, String fullname, String password) {
        verifyPayload(username, fullname, password);

        this.id = UUID.randomUUID();
        this.username = username;
        this.fullname = fullname;
        this.password = password;
    }

    private static void verifyPayload(String username, String fullname, String password) {
        requireNotBlank(username);
        requireNotBlank(fullname);
        requireNotBlank(password);

        if (username.length() > 50) {
            throw new IllegalArgumentException("REGISTER_USER.USERNAME_LIMIT_CHAR");
        }

        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new IllegalArgumentException("REGISTER_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER");
        }
    }

    private static void requireNotBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("REGISTER_USER.NOT_CONTAIN_NEEDED_PROPERTY");
        }
    }

    public UUID getId() {
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

    public void setPassword(String password) {
        this.password = password;
    }
}
