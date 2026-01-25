package forum.api.java.infrastructure.config;

import forum.api.java.applications.usecase.*;
import forum.api.java.infrastructure.repository.AuthenticationRepositoryImpl;
import forum.api.java.infrastructure.repository.ThreadCommentRepositoryImpl;
import forum.api.java.infrastructure.repository.ThreadRepositoryImpl;
import forum.api.java.infrastructure.repository.UserRepositoryImpl;
import forum.api.java.infrastructure.security.AuthenticationTokenManagerImpl;
import forum.api.java.infrastructure.security.GoogleCaptchaService;
import forum.api.java.infrastructure.security.PasswordHashImpl;
import forum.api.java.infrastructure.service.EmailServiceImpl;
import forum.api.java.infrastructure.service.PhoneNumberNormalizerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserRepositoryImpl userRepositoryImpl,
            PasswordHashImpl passwordHashImpl,
            GoogleCaptchaService googleCaptchaService,
            PhoneNumberNormalizerServiceImpl phoneNumberNormalizerServiceImpl
    ) {
        return new RegisterUserUseCase(
                userRepositoryImpl,
                passwordHashImpl,
                googleCaptchaService,
                phoneNumberNormalizerServiceImpl
        );
    }

    @Bean
    public LoginUserUseCase loginUserUseCase(
            UserRepositoryImpl userRepositoryImpl,
            AuthenticationRepositoryImpl authenticationRepositoryImpl,
            PasswordHashImpl passwordHashImpl,
            AuthenticationTokenManagerImpl authenticationTokenManagerImpl,
            GoogleCaptchaService googleCaptchaService
    ) {
        return new LoginUserUseCase(
                userRepositoryImpl,
                authenticationRepositoryImpl,
                passwordHashImpl,
                authenticationTokenManagerImpl,
                googleCaptchaService
        );
    }

    @Bean
    public CleanupExpiredTokenUseCase cleanupExpiredTokenUseCase(
            AuthenticationRepositoryImpl authenticationRepositoryImpl
    ) {
        return new CleanupExpiredTokenUseCase(authenticationRepositoryImpl);
    }

    @Bean
    public AddThreadUseCase addThreadUseCase(
            UserRepositoryImpl userRepositoryImpl,
            ThreadRepositoryImpl threadRepositoryImpl
    ) {
        return new AddThreadUseCase(
                userRepositoryImpl,
                threadRepositoryImpl
        );
    }

    @Bean
    public RefreshAuthenticationUseCase refreshAuthenticationUseCase(
            AuthenticationRepositoryImpl authenticationRepositoryImpl,
            AuthenticationTokenManagerImpl authenticationTokenManagerImpl
    ) {
        return new RefreshAuthenticationUseCase(authenticationRepositoryImpl, authenticationTokenManagerImpl);
    }

    @Bean
    public LogoutUserUseCase logoutUserUseCase(
            AuthenticationRepositoryImpl authenticationRepositoryImpl
    ) {
        return new LogoutUserUseCase(authenticationRepositoryImpl);
    }

    @Bean
    public GetThreadDetailUseCase getThreadDetailUseCase(
            ThreadRepositoryImpl threadRepositoryImpl
    ) {
        return new GetThreadDetailUseCase(threadRepositoryImpl);
    }

    @Bean
    public GetThreadPaginationListUseCase getThreadPaginationListUseCase(
            ThreadRepositoryImpl threadRepositoryImpl
    ) {
        return new GetThreadPaginationListUseCase(threadRepositoryImpl);
    }

    @Bean
    public UpdateThreadUseCase updateThreadUseCase(
            ThreadRepositoryImpl threadRepositoryImpl
    ) {
        return new UpdateThreadUseCase(threadRepositoryImpl);
    }

    @Bean
    public DeleteThreadUseCase deleteThreadUseCase(
            ThreadRepositoryImpl threadRepositoryImpl
    ) {
        return new DeleteThreadUseCase(threadRepositoryImpl);
    }

    @Bean
    public GetUserProfileUseCase getUserProfileUseCase(
            UserRepositoryImpl userRepositoryImpl
    ) {
        return new GetUserProfileUseCase(userRepositoryImpl);
    }

    @Bean
    public AddThreadCommentUseCase addThreadCommentUseCase(
            ThreadRepositoryImpl threadRepositoryImpl,
            UserRepositoryImpl userRepositoryImpl,
            ThreadCommentRepositoryImpl threadCommentRepositoryImpl
    ) {
        return new AddThreadCommentUseCase(
                threadRepositoryImpl,
                userRepositoryImpl,
                threadCommentRepositoryImpl
        );
    }

    @Bean
    public RequestResetPasswordLinkUseCase requestResetPasswordLinkUseCase(
            GoogleCaptchaService googleCaptchaService,
            PasswordHashImpl passwordHashImpl,
            AuthenticationRepositoryImpl authenticationRepositoryImpl,
            UserRepositoryImpl userRepositoryImpl,
            EmailServiceImpl emailServiceImpl
    ) {
        return new RequestResetPasswordLinkUseCase(
                googleCaptchaService,
                passwordHashImpl,
                authenticationRepositoryImpl,
                userRepositoryImpl,
                emailServiceImpl
        );
    }
}
