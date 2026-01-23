package forum.api.java.applications.usecase;

import forum.api.java.applications.security.CaptchaService;
import forum.api.java.applications.security.PasswordHash;
import forum.api.java.applications.service.PhoneNumberNormalizer;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.entity.RegisteredUser;

public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordHash passwordHash;
    private final CaptchaService captchaService;

    public RegisterUserUseCase(UserRepository userRepository, PasswordHash passwordHash, CaptchaService captchaService) {
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
        this.captchaService = captchaService;
    }

    public RegisteredUser execute(RegisterUser registerUser) {
        captchaService.verifyToken(registerUser.getCaptchaToken());
        userRepository.verifyAvailableUsername(registerUser.getUsername());
        String normalizedPhoneNumber = PhoneNumberNormalizer.normalize(registerUser.getPhoneNumber());
        registerUser.setPhoneNumber(normalizedPhoneNumber);
        String hashedPassword = passwordHash.hashPassword(registerUser.getPassword());
        registerUser.setPassword(hashedPassword);
        return userRepository.addUser(registerUser);
    }
}
