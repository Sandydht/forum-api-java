package forum.api.java.applications.usecase;

import forum.api.java.applications.security.AuthenticationTokenManager;
import forum.api.java.applications.security.CaptchaService;
import forum.api.java.applications.security.PasswordHash;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.authentication.entity.LoginUser;
import forum.api.java.domain.authentication.entity.NewAuthentication;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.UserDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Mock
    private CaptchaService captchaService;

    @InjectMocks
    private LoginUserUseCase loginUserUseCase;

    @Test
    @DisplayName("should orchestrating the get authentication action correctly")
    public void shouldOrchestratingTheGetAuthenticationActionCorrectly() {
        String userId = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        String password = "password123";
        String captchaToken = "captcha-token";

        String accessToken = "access-token";
        String refreshToken = "refresh-token";

        LoginUser loginUser = new LoginUser(username, password, captchaToken);
        UserDetail userDetail = new UserDetail(userId, username, fullname, password);

        Mockito.doNothing().when(captchaService).verifyToken(captchaToken);
        Mockito.when(userRepository.getUserByUsername(loginUser.getUsername())).thenReturn(userDetail);
        Mockito.doNothing().when(passwordHash).passwordCompare(loginUser.getPassword(), userDetail.getPassword());
        Mockito.when(authenticationTokenManager.createAccessToken(userId)).thenReturn(accessToken);
        Mockito.when(authenticationTokenManager.createRefreshToken(userId)).thenReturn(refreshToken);
        Mockito.doNothing().when(authenticationRepository).addToken(eq(username), eq(refreshToken));

        NewAuthentication loggedInUser = loginUserUseCase.execute(loginUser);

        Assertions.assertEquals(accessToken, loggedInUser.getAccessToken());
        Assertions.assertEquals(refreshToken, loggedInUser.getRefreshToken());

        Mockito.verify(captchaService, Mockito.times(1)).verifyToken(captchaToken);
        Mockito.verify(userRepository, Mockito.times(1)).getUserByUsername(loginUser.getUsername());
        Mockito.verify(passwordHash, Mockito.times(1)).passwordCompare(loginUser.getPassword(), userDetail.getPassword());
        Mockito.verify(authenticationTokenManager, Mockito.times(1)).createAccessToken(userId);
        Mockito.verify(authenticationTokenManager, Mockito.times(1)).createRefreshToken(userId);
        Mockito.verify(authenticationRepository, Mockito.times(1)).addToken(eq(username), eq(refreshToken));
    }
}
