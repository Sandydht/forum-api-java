package forum.api.java.domain.user;

import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.entity.RegisteredUser;

import java.util.UUID;

public interface UserRepository {
    default void addUser(RegisterUser registerUser) {
        throw new UnsupportedOperationException("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default boolean verifyAvailableUsername(String username) {
        throw new UnsupportedOperationException("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default RegisteredUser getUserByUsername(String username) {
        throw new UnsupportedOperationException("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }
}
