package forum.api.java.infrastructure.repository;

import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.RegisterUser;

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
}
