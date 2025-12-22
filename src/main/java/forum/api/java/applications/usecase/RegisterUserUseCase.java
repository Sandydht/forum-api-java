package forum.api.java.applications.usecase;

import forum.api.java.applications.security.PasswordHash;
import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.RegisteredUser;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordHash passwordHash;

    public RegisterUserUseCase(UserRepository userRepository, PasswordHash passwordHash) {
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
    }

    public RegisteredUser execute(RegisterUser registerUser) {
        userRepository.verifyAvailableUsername(registerUser.getUsername());
        String encryptedPassword = passwordHash.hashPassword(registerUser.getPassword());
        registerUser.setPassword(encryptedPassword);
        return userRepository.addUser(registerUser);
    }
}
