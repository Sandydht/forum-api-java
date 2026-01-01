package forum.api.java.infrastructure.config;

import forum.api.java.applications.security.AuthenticationTokenManager;
import forum.api.java.applications.usecase.*;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.infrastructure.repository.AuthenticationRepositoryImpl;
import forum.api.java.infrastructure.repository.ThreadRepositoryImpl;
import forum.api.java.infrastructure.repository.UserRepositoryImpl;
import forum.api.java.infrastructure.security.AuthenticationTokenManagerImpl;
import forum.api.java.infrastructure.security.PasswordHashImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserRepositoryImpl userRepositoryImpl,
            PasswordHashImpl passwordHashImpl
    ) {
        return new RegisterUserUseCase(
                userRepositoryImpl,
                passwordHashImpl
        );
    }

    @Bean
    public LoginUserUseCase loginUserUseCase(
            UserRepositoryImpl userRepositoryImpl,
            AuthenticationRepositoryImpl authenticationRepositoryImpl,
            PasswordHashImpl passwordHashImpl,
            AuthenticationTokenManagerImpl authenticationTokenManagerImpl
    ) {
        return new LoginUserUseCase(
                userRepositoryImpl,
                authenticationRepositoryImpl,
                passwordHashImpl,
                authenticationTokenManagerImpl
        );
    }

    @Bean
    public CleanupExpiredTokenUseCase cleanupExpiredTokenUseCase(AuthenticationRepositoryImpl authenticationRepositoryImpl) {
        return new CleanupExpiredTokenUseCase(authenticationRepositoryImpl);
    }

    @Bean
    public AddThreadUseCase addThreadUseCase(ThreadRepositoryImpl threadRepositoryImpl) {
        return new AddThreadUseCase(threadRepositoryImpl);
    }

    @Bean
    public RefreshAuthenticationUseCase refreshAuthenticationUseCase(
            AuthenticationRepository authenticationRepository,
            AuthenticationTokenManager authenticationTokenManager
    ) {
        return new RefreshAuthenticationUseCase(authenticationRepository, authenticationTokenManager);
    }

    @Bean
    public LogoutUserUseCase logoutUserUseCase(AuthenticationRepository authenticationRepository) {
        return new LogoutUserUseCase(authenticationRepository);
    }

    @Bean
    public GetThreadDetailUseCase getThreadDetailUseCase(ThreadRepository threadRepository) {
        return new GetThreadDetailUseCase(threadRepository);
    }

    @Bean
    public GetRefreshTokenUseCase getRefreshTokenUseCase(AuthenticationRepository authenticationRepository) {
        return new GetRefreshTokenUseCase(authenticationRepository);
    }
}
