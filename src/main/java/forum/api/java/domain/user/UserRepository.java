package forum.api.java.domain.user;

import forum.api.java.domain.user.entity.UserEntity;

import java.util.Optional;

public interface UserRepository {
    default UserEntity addUser(String username, String fullname, String password) {
        throw new UnsupportedOperationException("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default void verifyAvailableUsername(String username) {
        throw new UnsupportedOperationException("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default Optional<UserEntity> getUserByUsername(String username) {
        throw new UnsupportedOperationException("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default Optional<UserEntity> getUserById(String id) {
        throw new UnsupportedOperationException("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }
}
