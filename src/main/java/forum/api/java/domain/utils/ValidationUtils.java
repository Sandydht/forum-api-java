package forum.api.java.domain.utils;

import java.net.InetAddress;
import java.util.regex.Pattern;

public class ValidationUtils {
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[\\w]+$");
    private static final Pattern PASSWORD_MUST_CONTAIN_LETTERS_AND_NUMBERS_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d).+$");
    private static final Pattern PASSWORD_MUST_NOT_CONTAIN_SPACE_PATTERN = Pattern.compile(".*\\s.*");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^(?:\\+62|62|0)8[1-9][0-9]{6,10}$");

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

    public static void emailValidation(String email, String message) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void phoneNumberValidation(String phoneNumber, String message) {
        if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void validateIpAddress(String ip, String message) {
        try {
            InetAddress.getByName(ip);
        } catch (Exception e) {
            throw new IllegalArgumentException(message);
        }
    }
}
