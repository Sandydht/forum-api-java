package forum.api.java.infrastructure.repository;

import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.RegisterUser;

import java.util.UUID;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public RegisterUser addUser(RegisterUser registerUser) {
        return new RegisterUser(
                registerUser.getUsername(),
                registerUser.getFullname(),
                registerUser.getPassword()
        );
    }

    @Override
    public boolean verifyAvailableUsername(String username) {
        return true;
    }

    @Override
    public String getPasswordByUsername(String username) {
        return "password";
    }

    @Override
    public UUID getIdByUsername(String username) {
        return null;
    }
}
