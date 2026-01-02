package forum.api.java.applications.usecase;

import forum.api.java.applications.security.AuthenticationTokenManager;
import forum.api.java.applications.security.PasswordHash;
import forum.api.java.domain.authentication.AuthenticationRepository;
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

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;

@DisplayName("Login user use case")
@ExtendWith(MockitoExtension.class)
public class LoginUserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationRepository authenticationRepository;

    @Mock
    private PasswordHash passwordHash;

    @Mock
    private AuthenticationTokenManager authenticationTokenManager;

    @InjectMocks
    private LoginUserUseCase loginUserUseCase;

    @Test
    @DisplayName("should orchestrating the get authentication action correctly")
    public void shouldOrchestratingTheGetAuthenticationActionCorrectly() {
        String userId = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        String password = "password";

        String accessToken = "access-token";
        String refreshToken = "refresh-token";

        UserEntity userEntity = new UserEntity(userId, username, fullname, password);

        Mockito.when(userRepository.getUserByUsername(username)).thenReturn(Optional.of(userEntity));
        Mockito.doNothing().when(passwordHash).passwordCompare(password, userEntity.getPassword());
        Mockito.when(authenticationTokenManager.createAccessToken(userId)).thenReturn(accessToken);
        Mockito.when(authenticationTokenManager.createRefreshToken(userId)).thenReturn(refreshToken);
        Mockito.doNothing().when(authenticationRepository).addToken(eq(username), eq(refreshToken));

        String generatedAccessToken = loginUserUseCase.execute(username, password);

        Assertions.assertEquals(accessToken, generatedAccessToken);

        Mockito.verify(userRepository, Mockito.times(1)).getUserByUsername(username);
        Mockito.verify(passwordHash, Mockito.times(1)).passwordCompare(password, userEntity.getPassword());
        Mockito.verify(authenticationTokenManager, Mockito.times(1)).createAccessToken(userId);
        Mockito.verify(authenticationTokenManager, Mockito.times(1)).createRefreshToken(userId);
        Mockito.verify(authenticationRepository, Mockito.times(1)).addToken(eq(username), eq(refreshToken));
    }
}
