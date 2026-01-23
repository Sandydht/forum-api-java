package forum.api.java.domain.utils;

import java.util.regex.Pattern;

public class ValidationUtils {
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[\\w]+$");
    private static final Pattern PASSWORD_MUST_CONTAIN_LETTERS_AND_NUMBERS_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d).+$");
    private static final Pattern PASSWORD_MUST_NOT_CONTAIN_SPACE_PATTERN = Pattern.compile(".*\\s.*");

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

    public static void passwordLimitCharacter(String password, String message) {
        if (password.length() < 8) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void passwordMustContainLettersAndNumber(String password, String message) {
        if (!PASSWORD_MUST_CONTAIN_LETTERS_AND_NUMBERS_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void passwordMustNotContainSpace(String password, String message) {
        if (PASSWORD_MUST_NOT_CONTAIN_SPACE_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException(message);
        }
    }
}
