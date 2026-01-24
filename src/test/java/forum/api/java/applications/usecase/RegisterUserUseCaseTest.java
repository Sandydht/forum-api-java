package forum.api.java.applications.usecase;

import forum.api.java.applications.security.CaptchaService;
import forum.api.java.applications.security.PasswordHash;
import forum.api.java.applications.service.PhoneNumberNormalizerService;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.entity.RegisteredUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@DisplayName("Register user use case")
@ExtendWith(MockitoExtension.class)
public class RegisterUserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHash passwordHash;

    @Mock
    private CaptchaService captchaService;

    @Mock
    private PhoneNumberNormalizerService phoneNumberNormalizerService;

    @InjectMocks
    private RegisterUserUseCase registerUserUseCase;

    @Test
    @DisplayName("should orchestrating the register user action correctly")
    public void shouldOrchestratingTheRegisterUserActionCorrectly() {
        String id = UUID.randomUUID().toString();
        String username = "user";
        String email = "example@email.com";
        String phoneNumber = "+6281123123123";
        String fullname = "Fullname";
        String password = "password123";
        String hashedPassword = "hashedPassword";
        String captchaToken = "captcha-token";

        RegisterUser registerUser = new RegisterUser(username, email, phoneNumber, fullname, password, captchaToken);

        Mockito.when(phoneNumberNormalizerService.normalize(phoneNumber)).thenReturn("+6281123123123");
        Mockito.when(passwordHash.hashPassword(password)).thenReturn(hashedPassword);
        Mockito.doNothing().when(captchaService).verifyToken(captchaToken);
        Mockito.doNothing().when(userRepository).verifyAvailableUsername(username);
        Mockito.doNothing().when(userRepository).verifyAvailableEmail(email);
        Mockito.doNothing().when(userRepository).verifyAvailablePhoneNumber(phoneNumber);
        Mockito.when(userRepository.addUser(registerUser)).thenReturn(new RegisteredUser(
                id,
                registerUser.getUsername(),
                registerUser.getEmail(),
                registerUser.getPhoneNumber(),
                registerUser.getFullname()
        ));

        RegisteredUser registeredUser = registerUserUseCase.execute(registerUser);

        Assertions.assertEquals(id, registeredUser.getId());
        Assertions.assertEquals(username, registeredUser.getUsername());
        Assertions.assertEquals(fullname, registeredUser.getFullname());

        Assertions.assertEquals("+6281123123123", registerUser.getPhoneNumber());
        Assertions.assertEquals(hashedPassword, registerUser.getPassword());

        Mockito.verify(phoneNumberNormalizerService, Mockito.times(1)).normalize(phoneNumber);
        Mockito.verify(passwordHash, Mockito.times(1)).hashPassword(password);
        Mockito.verify(captchaService, Mockito.times(1)).verifyToken(captchaToken);
        Mockito.verify(userRepository, Mockito.times(1)).verifyAvailableUsername(username);
        Mockito.verify(userRepository, Mockito.times(1)).verifyAvailableEmail(email);
        Mockito.verify(userRepository, Mockito.times(1)).verifyAvailablePhoneNumber(phoneNumber);
        Mockito.verify(userRepository, Mockito.times(1)).addUser(registerUser);
    }
}
