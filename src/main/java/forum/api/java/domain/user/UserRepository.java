package forum.api.java.domain.user;

import forum.api.java.domain.user.entity.RegisterUser;

import java.util.UUID;

public interface UserRepository {
    default RegisterUser addUser(RegisterUser registerUser) {
        throw new UnsupportedOperationException("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default boolean verifyAvailableUsername(String username) {
        throw new UnsupportedOperationException("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default String getPasswordByUsername(String username) {
        throw new UnsupportedOperationException("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default UUID getIdByUsername(String username) {
        throw new UnsupportedOperationException("USER_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }
}
