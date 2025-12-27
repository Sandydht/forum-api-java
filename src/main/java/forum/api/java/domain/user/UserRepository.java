package forum.api.java.domain.user;

import forum.api.java.domain.user.entity.UserDetail;
import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.entity.RegisteredUser;

import java.util.Optional;

public interface UserRepository {
    default RegisteredUser addUser(RegisterUser registerUser) {
        throw new UnsupportedOperationException("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default void verifyAvailableUsername(String username) {
        throw new UnsupportedOperationException("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default Optional<UserDetail> getUserByUsername(String username) {
        throw new UnsupportedOperationException("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default Optional<UserDetail> getUserById(String id) {
        throw new UnsupportedOperationException("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }
}
