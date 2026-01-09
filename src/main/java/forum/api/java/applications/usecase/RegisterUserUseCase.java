package forum.api.java.applications.usecase;

import forum.api.java.applications.security.PasswordHash;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.RegisteredUser;

public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordHash passwordHash;

    public RegisterUserUseCase(UserRepository userRepository, PasswordHash passwordHash) {
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
    }

    public RegisteredUser execute(String username, String fullname, String password) {
        userRepository.verifyAvailableUsername(username);
        String hashedPassword = passwordHash.hashPassword(password);
        return userRepository.addUser(username, fullname, hashedPassword);
    }
}
