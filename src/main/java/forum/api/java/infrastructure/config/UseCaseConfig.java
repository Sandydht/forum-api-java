package forum.api.java.infrastructure.config;

import forum.api.java.applications.usecase.AddThreadUseCase;
import forum.api.java.applications.usecase.CleanupExpiredTokenUseCase;
import forum.api.java.applications.usecase.LoginUserUseCase;
import forum.api.java.applications.usecase.RegisterUserUseCase;
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
    public AddThreadUseCase addThreadUseCase(
            UserRepositoryImpl userRepositoryImpl,
            ThreadRepositoryImpl threadRepositoryImpl
    ) {
        return new AddThreadUseCase(userRepositoryImpl, threadRepositoryImpl);
    }
}
