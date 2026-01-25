package forum.api.java.commons.exceptions;

import java.util.HashMap;
import java.util.Map;

public class DomainErrorTranslator {
    private static  final Map<String, RuntimeException> directories = new HashMap<>();

    static {
        // REGISTER_USER
        directories.put("REGISTER_USER.NOT_CONTAIN_NEEDED_PROPERTY", new InvariantException("Cannot create new user because required property is missing"));
        directories.put("REGISTER_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER", new InvariantException("Cannot create a new user because the username contains prohibited characters"));
        directories.put("REGISTER_USER.USERNAME_LIMIT_CHAR", new InvariantException("Cannot create a new user because the username character exceeds the limit"));
        directories.put("REGISTER_USER.EMAIL_IS_INVALID", new InvariantException("Cannot create a new user because the email is invalid"));
        directories.put("REGISTER_USER.PHONE_NUMBER_IS_INVALID", new InvariantException("Cannot create a new user because the phone number is invalid"));
        directories.put("REGISTER_USER.PASSWORD_MUST_BE_AT_LEAST_8_CHARACTERS", new InvariantException("Cannot create a new user because the password less than 8 characters"));
        directories.put("REGISTER_USER.PASSWORD_MUST_CONTAIN_LETTERS_AND_NUMBERS", new InvariantException("Cannot create a new user because the password not contain letters and numbers"));
        directories.put("REGISTER_USER.PASSWORD_MUST_NOT_CONTAIN_SPACE", new InvariantException("Cannot create a new user because the password contain space"));

        // LOGIN_USER
        directories.put("LOGIN_USER.NOT_CONTAIN_NEEDED_PROPERTY", new InvariantException("Must send username and password"));
        directories.put("LOGIN_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER", new InvariantException("Username contains prohibited characters"));
        directories.put("LOGIN_USER.USERNAME_LIMIT_CHAR", new InvariantException("Username character exceeds the limit"));
        directories.put("LOGIN_USER.PASSWORD_MUST_BE_AT_LEAST_8_CHARACTERS", new InvariantException("Password less than 8 characters"));
        directories.put("LOGIN_USER.PASSWORD_MUST_CONTAIN_LETTERS_AND_NUMBERS", new InvariantException("Password not contain letters and numbers"));
        directories.put("LOGIN_USER.PASSWORD_MUST_NOT_CONTAIN_SPACE", new InvariantException("Password contain space"));

        // PASSWORD_HASH_IMPL
        directories.put("PASSWORD_HASH_IMPL.INCORRECT_CREDENTIALS", new AuthenticationException("Incorrect credentials"));

        // USER_REPOSITORY_IMPL
        directories.put("USER_REPOSITORY_IMPL.USER_ALREADY_EXIST", new InvariantException("User already exist"));
        directories.put("USER_REPOSITORY_IMPL.USER_NOT_FOUND", new NotFoundException("User not found"));
        directories.put("USER_REPOSITORY_IMPL.EMAIL_ALREADY_EXIST", new InvariantException("Email already exist"));
        directories.put("USER_REPOSITORY_IMPL.PHONE_NUMBER_ALREADY_EXIST", new InvariantException("Phone number already exist"));

        // AUTHENTICATION_REPOSITORY_IMPL
        directories.put("AUTHENTICATION_REPOSITORY_IMPL.USER_NOT_FOUND", new NotFoundException("User not found"));
        directories.put("AUTHENTICATION_REPOSITORY_IMPL.TOKEN_NOT_FOUND", new NotFoundException("Token not found"));

        // THREAD_REPOSITORY_IMPL
        directories.put("THREAD_REPOSITORY_IMPL.USER_NOT_FOUND", new NotFoundException("User not found"));
        directories.put("THREAD_REPOSITORY_IMPL.THREAD_NOT_FOUND", new NotFoundException("Thread not found"));

        // REQUEST_RESET_PASSWORD_LINK
        directories.put("REQUEST_RESET_PASSWORD_LINK.EMAIL_IS_INVALID", new InvariantException("Email is invalid"));
        directories.put("REQUEST_RESET_PASSWORD_LINK.IP_ADDRESS_IS_INVALID", new InvariantException("IP address is invalid"));
    }

    public RuntimeException translate(Exception error) {
        RuntimeException translated = directories.get(error.getMessage());

        if (translated != null) {
            return translated;
        }

        if (error instanceof RuntimeException) {
            return (RuntimeException) error;
        }

        return new RuntimeException(error.getMessage());
    }
}
