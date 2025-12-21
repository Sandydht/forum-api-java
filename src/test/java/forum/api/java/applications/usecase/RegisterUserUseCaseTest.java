package forum.api.java.applications.usecase;

import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.entity.RegisteredUser;
import forum.api.java.infrastructure.repository.UserRepositoryImpl;
import forum.api.java.infrastructure.security.PasswordHashImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Register user use case")
@ExtendWith(MockitoExtension.class)
public class RegisterUserUseCaseTest {
    @Mock
    private UserRepositoryImpl userRepositoryImpl;

    @Mock
    private PasswordHashImpl passwordHashImpl;

    private RegisterUserUseCase registerUserUseCase;

    @BeforeEach
    public void setUp() {
        registerUserUseCase = new RegisterUserUseCase(userRepositoryImpl, passwordHashImpl);
    }

    @Test
    @DisplayName("should orchestrating the register user action correctly")
    public void testRegisterUserActionCorrectly() {
        String username = "user";
        String password = "password";
        String fullname = "Fullname";

        RegisterUser registerUser = new RegisterUser(username, fullname, password);

        Mockito.when(userRepositoryImpl.verifyAvailableUsername(username)).thenReturn(true);
        Mockito.when(passwordHashImpl.hashPassword(password)).thenReturn("hashedPassword");
        Mockito.when(userRepositoryImpl.addUser(registerUser)).thenReturn(registerUser);

        RegisteredUser registeredUser = registerUserUseCase.execute(registerUser);

        Assertions.assertNotNull(registeredUser.getId());
        Assertions.assertEquals(username, registeredUser.getUsername());
        Assertions.assertEquals(fullname, registeredUser.getFullname());
    }
}
