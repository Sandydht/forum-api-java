package forum.api.java.applications.usecase;

import forum.api.java.applications.security.AuthenticationTokenManager;
import forum.api.java.applications.security.PasswordHash;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.authentication.entity.NewAuth;
import forum.api.java.domain.user.entity.UserDetail;
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

import java.time.Duration;
import java.time.Instant;
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
        String id = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        String password = "password";
        String hashedPassword = "hashedPassword";
        String fakeAccessToken = "fake-access-token";
        String fakeRefreshToken = "fake-refresh-token";

        UserLogin userLogin = new UserLogin(username, password);
        UserDetail userDetail = new UserDetail(id, username, fullname, hashedPassword);

        Mockito.when(userRepository.getUserByUsername(username)).thenReturn(Optional.of(userDetail));
        Mockito.doNothing().when(passwordHash).passwordCompare(userLogin.getPassword(), userDetail.getPassword());
        Mockito.when(authenticationTokenManager.createAccessToken(id)).thenReturn(fakeAccessToken);
        Mockito.when(authenticationTokenManager.createRefreshToken(id)).thenReturn(fakeRefreshToken);
        Mockito.doNothing().when(authenticationRepository).addToken(
                Mockito.eq(userDetail),
                Mockito.eq(fakeRefreshToken),
                Mockito.any(Instant.class)
        );

        NewAuth newAuth = loginUserUseCase.execute(userLogin);

        Assertions.assertEquals(fakeAccessToken, newAuth.getAccessToken());
        Assertions.assertEquals(fakeRefreshToken, newAuth.getRefreshToken());

        Mockito.verify(userRepository, Mockito.times(1)).getUserByUsername(username);
        Mockito.verify(passwordHash, Mockito.times(1)).passwordCompare(password, hashedPassword);
        Mockito.verify(authenticationTokenManager, Mockito.times(1)).createAccessToken(id);
        Mockito.verify(authenticationTokenManager, Mockito.times(1)).createRefreshToken(id);
        Mockito.verify(authenticationRepository, Mockito.times(1)).addToken(
                Mockito.eq(userDetail),
                Mockito.eq(fakeRefreshToken),
                Mockito.any(Instant.class)
        );
    }
}
