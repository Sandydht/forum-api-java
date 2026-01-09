package forum.api.java.applications.usecase;

import forum.api.java.applications.security.AuthenticationTokenManager;
import forum.api.java.applications.security.PasswordHash;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.authentication.entity.NewAuthentication;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.UserDetail;

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

    public NewAuthentication execute(String username, String password) {
        UserDetail userDetail = userRepository.getUserByUsername(username);
        passwordHash.passwordCompare(password, userDetail.getPassword());

        String accessToken = authenticationTokenManager.createAccessToken(userDetail.getId());
        String refreshToken = authenticationTokenManager.createRefreshToken(userDetail.getId());

        authenticationRepository.addToken(username, refreshToken);

        return new NewAuthentication(accessToken, refreshToken);
    }
}
