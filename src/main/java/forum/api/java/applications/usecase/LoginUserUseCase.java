package forum.api.java.applications.usecase;

import forum.api.java.applications.security.AuthenticationTokenManager;
import forum.api.java.applications.security.PasswordHash;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.authentication.entity.NewAuth;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.UserLogin;

import java.util.UUID;

public class LoginUserUseCase {
    private final UserRepository userRepository;
    private final PasswordHash passwordHash;
    private final AuthenticationTokenManager authenticationTokenManager;
    private final AuthenticationRepository authenticationRepository;

    public LoginUserUseCase(UserRepository userRepository, PasswordHash passwordHash, AuthenticationTokenManager authenticationTokenManager, AuthenticationRepository authenticationRepository) {
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
        this.authenticationTokenManager = authenticationTokenManager;
        this.authenticationRepository = authenticationRepository;
    }

    public NewAuth execute(UserLogin userLogin) {
        String encryptedPassword = userRepository.getPasswordByUsername(userLogin.getUsername());

        if (!passwordHash.passwordCompare(userLogin.getPassword(), encryptedPassword)) {
            throw new IllegalArgumentException("Incorrect credentials");
        }

        UUID userId = userRepository.getIdByUsername(userLogin.getUsername());
        String accessToken = authenticationTokenManager.createAccessToken(userId);
        String refreshToken = authenticationTokenManager.createRefreshToken(userId);

        authenticationRepository.addToken(refreshToken);

        return new NewAuth(
                accessToken,
                refreshToken
        );
    }
}
