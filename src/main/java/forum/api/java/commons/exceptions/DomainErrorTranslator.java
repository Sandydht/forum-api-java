package forum.api.java.commons.exceptions;

import java.util.HashMap;
import java.util.Map;

public class DomainErrorTranslator {
    private static  final Map<String, RuntimeException> directories = new HashMap<>();

    static {
        // REGISTER_USER
        directories.put("REGISTER_USER.NOT_CONTAIN_NEEDED_PROPERTY", new InvariantException("Cannot create new user because required property is missing"));
        directories.put("REGISTER_USER.USERNAME_LIMIT_CHAR", new InvariantException("Cannot create a new user because the username character exceeds the limit"));
        directories.put("REGISTER_USER.NOT_MEET_DATA_TYPE_SPECIFICATION", new InvariantException("Cannot create new user because data type does not match"));
        directories.put("REGISTER_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER", new InvariantException("Cannot create a new user because the username contains prohibited characters"));

        // USER_LOGIN
        directories.put("USER_LOGIN.NOT_CONTAIN_NEEDED_PROPERTY", new InvariantException("Must send username and password"));
        directories.put("USER_LOGIN.USERNAME_LIMIT_CHAR", new InvariantException("Username character exceeds the limit"));
        directories.put("USER_LOGIN.USERNAME_CONTAIN_RESTRICTED_CHARACTER", new InvariantException("Username contains prohibited characters"));

        // PASSWORD_HASH_IMPL
        directories.put("PASSWORD_HASH_IMPL.INCORRECT_CREDENTIALS", new AuthenticationException("Incorrect credentials"));
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
