package forum.api.java.domain.user.entity;

import java.util.regex.Pattern;

public class UserLogin {
    private final String username;
    private final String password;

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[\\w]+$");

    public UserLogin(String username, String password) {
        verifyPayload(username, password);

        this.username = username;
        this.password = password;
    }

    private static void verifyPayload(String username, String password) {
        requireNotBlank(username);
        requireNotBlank(password);

        if (username.length() > 50) {
            throw new IllegalArgumentException("USER_LOGIN.USERNAME_LIMIT_CHAR");
        }

        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new IllegalArgumentException("USER_LOGIN.USERNAME_CONTAIN_RESTRICTED_CHARACTER");
        }
    }

    private static void requireNotBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("USER_LOGIN.USERNAME_CONTAIN_RESTRICTED_CHARACTER");
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
