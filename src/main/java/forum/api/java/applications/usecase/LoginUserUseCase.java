package forum.api.java.applications.usecase;

import forum.api.java.applications.security.AuthenticationTokenManager;
import forum.api.java.applications.security.CaptchaService;
import forum.api.java.applications.security.PasswordHash;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.authentication.entity.LoginUser;
import forum.api.java.domain.authentication.entity.NewAuthentication;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.UserDetail;

public class LoginUserUseCase {
    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;
    private final PasswordHash passwordHash;
    private final AuthenticationTokenManager authenticationTokenManager;
    private final CaptchaService captchaService;

    public LoginUserUseCase(
            UserRepository userRepository,
            AuthenticationRepository authenticationRepository,
            PasswordHash passwordHash,
            AuthenticationTokenManager authenticationTokenManager,
            CaptchaService captchaService
    ) {
        this.userRepository = userRepository;
        this.authenticationRepository = authenticationRepository;
        this.passwordHash = passwordHash;
        this.authenticationTokenManager = authenticationTokenManager;
        this.captchaService = captchaService;
    }

    public NewAuthentication execute(LoginUser loginUser) {
        captchaService.verifyToken(loginUser.getCaptchaToken());
        UserDetail userDetail = userRepository.getUserByUsername(loginUser.getUsername());
        passwordHash.passwordCompare(loginUser.getPassword(), userDetail.getPassword());
        String accessToken = authenticationTokenManager.createAccessToken(userDetail.getId());
        String refreshToken = authenticationTokenManager.createRefreshToken(userDetail.getId());
        authenticationRepository.addToken(loginUser.getUsername(), refreshToken);
        return new NewAuthentication(accessToken, refreshToken);
    }
}
