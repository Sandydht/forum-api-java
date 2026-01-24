package forum.api.java.applications.usecase;

import forum.api.java.applications.security.CaptchaService;
import forum.api.java.applications.security.PasswordHash;
import forum.api.java.applications.service.PhoneNumberNormalizerService;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.entity.RegisteredUser;

public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordHash passwordHash;
    private final CaptchaService captchaService;
    private final PhoneNumberNormalizerService phoneNumberNormalizerService;

    public RegisterUserUseCase(
            UserRepository userRepository,
            PasswordHash passwordHash,
            CaptchaService captchaService,
            PhoneNumberNormalizerService phoneNumberNormalizerService
    ) {
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
        this.captchaService = captchaService;
        this.phoneNumberNormalizerService = phoneNumberNormalizerService;
    }

    public RegisteredUser execute(RegisterUser registerUser) {
        String normalizedPhoneNumber = phoneNumberNormalizerService.normalize(registerUser.getPhoneNumber());
        registerUser.setPhoneNumber(normalizedPhoneNumber);

        String hashedPassword = passwordHash.hashPassword(registerUser.getPassword());
        registerUser.setPassword(hashedPassword);

        captchaService.verifyToken(registerUser.getCaptchaToken());

        userRepository.verifyAvailableUsername(registerUser.getUsername());
        userRepository.verifyAvailableEmail(registerUser.getEmail());
        userRepository.verifyAvailablePhoneNumber(registerUser.getPhoneNumber());

        return userRepository.addUser(registerUser);
    }
}
