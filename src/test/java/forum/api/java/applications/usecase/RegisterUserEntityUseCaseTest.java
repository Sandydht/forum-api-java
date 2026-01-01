package forum.api.java.applications.usecase;

import forum.api.java.applications.security.PasswordHash;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.UserEntity;
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
public class RegisterUserEntityUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHash passwordHash;

    @InjectMocks
    private RegisterUserUseCase registerUserUseCase;

    @Test
    @DisplayName("should orchestrating the register user action correctly")
    public void testRegisterUserActionCorrectly() {
        String id = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        String password = "password";
        String hashedPassword = "hashedPassword";

        Mockito.doNothing().when(userRepository).verifyAvailableUsername(username);
        Mockito.when(passwordHash.hashPassword(password)).thenReturn(hashedPassword);
        Mockito.when(userRepository.addUser(username, fullname, hashedPassword)).thenReturn(new UserEntity(id, username, fullname, hashedPassword));

        UserEntity userEntity = registerUserUseCase.execute(username, fullname, password);

        Assertions.assertEquals(id, userEntity.getId());
        Assertions.assertEquals(username, userEntity.getUsername());
        Assertions.assertEquals(fullname, userEntity.getFullname());
        Assertions.assertEquals(hashedPassword, userEntity.getPassword());

        Mockito.verify(userRepository, Mockito.times(1)).verifyAvailableUsername(username);
        Mockito.verify(passwordHash, Mockito.times(1)).hashPassword(password);
        Mockito.verify(userRepository, Mockito.times(1)).addUser(username, fullname, hashedPassword);
    }
}
