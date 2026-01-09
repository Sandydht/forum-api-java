package forum.api.java.domain.utils;

import java.util.regex.Pattern;

public class ValidationUtils {
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[\\w]+$");

    public static void requireNotBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void usernameLimitCharacter(String username, String message) {
        if (username.length() > 50) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void usernameNotContainRestrictedCharacter(String username, String message) {
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void requireNonNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
