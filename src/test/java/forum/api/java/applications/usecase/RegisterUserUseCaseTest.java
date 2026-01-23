package forum.api.java.applications.usecase;

import forum.api.java.applications.security.PasswordHash;
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

    @InjectMocks
    private RegisterUserUseCase registerUserUseCase;

    @Test
    @DisplayName("should orchestrating the register user action correctly")
    public void shouldOrchestratingThreRegisterUserActionCorrectly() {
        String id = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        String password = "password123";
        String hashedPassword = "hashedPassword";
        String captchaToken = "captcha-token";

        RegisterUser registerUser = new RegisterUser(username, fullname, password, captchaToken);

        Mockito.doNothing().when(userRepository).verifyAvailableUsername(username);
        Mockito.when(passwordHash.hashPassword(password)).thenReturn(hashedPassword);
        Mockito.when(userRepository.addUser(registerUser)).thenReturn(new RegisteredUser(id, username, fullname));

        RegisteredUser registeredUser = registerUserUseCase.execute(registerUser);

        Assertions.assertEquals(id, registeredUser.getId());
        Assertions.assertEquals(username, registeredUser.getUsername());
        Assertions.assertEquals(fullname, registeredUser.getFullname());
        Assertions.assertEquals(hashedPassword, registerUser.getPassword());

        Mockito.verify(userRepository, Mockito.times(1)).verifyAvailableUsername(username);
        Mockito.verify(passwordHash, Mockito.times(1)).hashPassword(password);
        Mockito.verify(userRepository, Mockito.times(1)).addUser(registerUser);
    }
}
