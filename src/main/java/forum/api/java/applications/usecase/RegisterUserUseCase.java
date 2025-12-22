package forum.api.java.applications.usecase;

import forum.api.java.applications.security.PasswordHash;
import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.RegisteredUser;

import java.util.Optional;

public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordHash passwordHash;

    public RegisterUserUseCase(UserRepository userRepository, PasswordHash passwordHash) {
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
    }

    public Optional<RegisteredUser> execute(RegisterUser registerUser) {
        if (userRepository.verifyAvailableUsername(registerUser.getUsername())) {
            throw new IllegalStateException("User already exist");
        }

        String encryptedPassword = passwordHash.hashPassword(registerUser.getPassword());
        registerUser.setPassword(encryptedPassword);

        return userRepository.addUser(registerUser);
    }
}
