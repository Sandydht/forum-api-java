package forum.api.java.applications.usecase;

import forum.api.java.applications.security.AuthenticationTokenManager;
import forum.api.java.applications.security.PasswordHash;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.authentication.entity.NewAuth;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.UserLogin;
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
    public void testLoginUserActionCorrectly() {
        UUID id = UUID.randomUUID();
        String username = "user";
        String password = "password";
        String hashedPassword = "hashedPassword";
        String fakeAccessToken = "fake-access-token";
        String fakeRefreshToken = "fake-refresh-token";

        UserLogin userLogin = new UserLogin(username, password);

        Mockito.when(userRepository.getPasswordByUsername(username)).thenReturn(Optional.of(hashedPassword));
        Mockito.when(passwordHash.passwordCompare(password, hashedPassword)).thenReturn(true);
        Mockito.when(userRepository.getIdByUsername(username)).thenReturn(Optional.of(id));
        Mockito.when(authenticationTokenManager.createAccessToken(id)).thenReturn(fakeAccessToken);
        Mockito.when(authenticationTokenManager.createRefreshToken(id)).thenReturn(fakeRefreshToken);
        Mockito.doNothing().when(authenticationRepository).addToken(fakeRefreshToken);

        NewAuth newAuth = loginUserUseCase.execute(userLogin).orElseThrow();

        Assertions.assertEquals(fakeAccessToken, newAuth.getAccessToken());
        Assertions.assertEquals(fakeRefreshToken, newAuth.getRefreshToken());

        Mockito.verify(userRepository, Mockito.times(1)).getPasswordByUsername(username);
        Mockito.verify(passwordHash, Mockito.times(1)).passwordCompare(password, hashedPassword);
        Mockito.verify(userRepository, Mockito.times(1)).getIdByUsername(username);
        Mockito.verify(authenticationTokenManager, Mockito.times(1)).createAccessToken(id);
        Mockito.verify(authenticationTokenManager, Mockito.times(1)).createRefreshToken(id);
        Mockito.verify(authenticationRepository, Mockito.times(1)).addToken(fakeRefreshToken);
    }
}
