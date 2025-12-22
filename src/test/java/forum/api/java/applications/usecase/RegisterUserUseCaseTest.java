package forum.api.java.applications.usecase;

import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.entity.RegisteredUser;
import forum.api.java.infrastructure.repository.UserRepositoryImpl;
import forum.api.java.infrastructure.security.PasswordHashImpl;
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
    private UserRepositoryImpl userRepositoryImpl;

    @Mock
    private PasswordHashImpl passwordHashImpl;

    @InjectMocks
    private RegisterUserUseCase registerUserUseCase;

    @Test
    @DisplayName("should orchestrating the register user action correctly")
    public void testRegisterUserActionCorrectly() {
        UUID id = UUID.randomUUID();
        String username = "user";
        String password = "password";
        String fullname = "Fullname";

        RegisterUser registerUser = new RegisterUser(username, fullname, password);

        Mockito.when(userRepositoryImpl.verifyAvailableUsername(username)).thenReturn(true);
        Mockito.when(passwordHashImpl.hashPassword(password)).thenReturn("hashedPassword");
        Mockito.doNothing().when(userRepositoryImpl).addUser(registerUser);
        Mockito.when(userRepositoryImpl.getUserByUsername(username)).thenReturn(new RegisteredUser(
                id,
                username,
                fullname
        ));

        RegisteredUser registeredUser = registerUserUseCase.execute(registerUser);

        Assertions.assertEquals(id, registeredUser.getId());
        Assertions.assertEquals(username, registeredUser.getUsername());
        Assertions.assertEquals(fullname, registeredUser.getFullname());

        Mockito.verify(userRepositoryImpl, Mockito.times(1)).verifyAvailableUsername(username);
        Mockito.verify(passwordHashImpl, Mockito.times(1)).hashPassword(password);
        Mockito.verify(userRepositoryImpl, Mockito.times(1)).addUser(registerUser);
        Mockito.verify(userRepositoryImpl, Mockito.times(1)).getUserByUsername(username);
    }
}
