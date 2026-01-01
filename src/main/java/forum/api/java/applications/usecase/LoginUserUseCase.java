package forum.api.java.applications.usecase;

import forum.api.java.applications.security.AuthenticationTokenManager;
import forum.api.java.applications.security.PasswordHash;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.UserEntity;

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

    public String execute(String username, String password) {
        UserEntity findUserEntity = userRepository.getUserByUsername(username).orElseThrow();
        passwordHash.passwordCompare(password, findUserEntity.getPassword());

        String accessToken = authenticationTokenManager.createAccessToken(findUserEntity.getId());
        String refreshToken = authenticationTokenManager.createRefreshToken(findUserEntity.getId());

        UserEntity userEntity = new UserEntity(findUserEntity.getId(), findUserEntity.getUsername(), findUserEntity.getFullname(), findUserEntity.getPassword());
        authenticationRepository.addToken(userEntity, refreshToken);

        return accessToken;
    }
}
