package forum.api.java.applications.usecase;

import forum.api.java.applications.security.AuthenticationTokenManager;
import forum.api.java.applications.security.PasswordHash;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.authentication.entity.NewAuth;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.UserDetail;
import forum.api.java.domain.user.entity.UserLogin;

import java.time.Duration;
import java.time.Instant;

public class LoginUserUseCase {
    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;
    private final PasswordHash passwordHash;
    private final AuthenticationTokenManager authenticationTokenManager;

    public LoginUserUseCase(
            UserRepository userRepository,
            AuthenticationRepository authenticationRepository,
            PasswordHash passwordHash,
            AuthenticationTokenManager authenticationTokenManager
    ) {
        this.userRepository = userRepository;
        this.authenticationRepository = authenticationRepository;
        this.passwordHash = passwordHash;
        this.authenticationTokenManager = authenticationTokenManager;
    }

    public NewAuth execute(UserLogin userLogin) {
        UserDetail userDetail = userRepository.getUserByUsername(userLogin.getUsername()).orElseThrow();
        passwordHash.passwordCompare(userLogin.getPassword(), userDetail.getPassword());

        String accessToken = authenticationTokenManager.createAccessToken(userDetail.getId());
        String refreshToken = authenticationTokenManager.createRefreshToken(userDetail.getId());

        Instant expiresAt = Instant.now().plus(Duration.ofDays(7));
        authenticationRepository.addToken(userDetail, refreshToken, expiresAt);

        return new NewAuth(accessToken, refreshToken);
    }
}
