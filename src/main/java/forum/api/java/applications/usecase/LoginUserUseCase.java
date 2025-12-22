package forum.api.java.applications.usecase;

import forum.api.java.applications.security.AuthenticationTokenManager;
import forum.api.java.applications.security.PasswordHash;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.authentication.entity.NewAuth;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.UserLogin;

import java.util.Optional;

public class LoginUserUseCase {
    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;
    private final PasswordHash passwordHash;
    private final AuthenticationTokenManager authenticationTokenManager;

    public LoginUserUseCase(UserRepository userRepository, AuthenticationRepository authenticationRepository, PasswordHash passwordHash, AuthenticationTokenManager authenticationTokenManager) {
        this.userRepository = userRepository;
        this.authenticationRepository = authenticationRepository;
        this.passwordHash = passwordHash;
        this.authenticationTokenManager = authenticationTokenManager;
    }

    public Optional<NewAuth> execute(UserLogin userLogin) {
        String hashedPassword = userRepository.getPasswordByUsername(userLogin.getUsername()).orElseThrow();

        if (!passwordHash.passwordCompare(userLogin.getPassword(), hashedPassword)) {
            throw new IllegalStateException("The credentials you entered are incorrect.");
        }

        return userRepository.getIdByUsername(userLogin.getUsername())
                .map(userId -> {
                    String accessToken = authenticationTokenManager.createAccessToken(userId);
                    String refreshToken = authenticationTokenManager.createRefreshToken(userId);

                    authenticationRepository.addToken(refreshToken);

                    return new NewAuth(
                        accessToken,
                        refreshToken
                    );
                });
    }
}
