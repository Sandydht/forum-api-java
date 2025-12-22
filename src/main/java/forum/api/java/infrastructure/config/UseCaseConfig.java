package forum.api.java.infrastructure.config;

import forum.api.java.applications.usecase.RegisterUserUseCase;
import forum.api.java.infrastructure.repository.UserRepositoryImpl;
import forum.api.java.infrastructure.security.PasswordHashImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public RegisterUserUseCase registerUserUseCase(UserRepositoryImpl userRepositoryImpl, PasswordHashImpl passwordHashImpl) {
        return new RegisterUserUseCase(userRepositoryImpl, passwordHashImpl);
    }
}
