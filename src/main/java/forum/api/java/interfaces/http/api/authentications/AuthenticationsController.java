package forum.api.java.interfaces.http.api.authentications;

import forum.api.java.applications.usecase.LoginUserUseCase;
import forum.api.java.applications.usecase.LogoutUserUseCase;
import forum.api.java.applications.usecase.RefreshAuthenticationUseCase;
import forum.api.java.applications.usecase.RequestResetPasswordLinkUseCase;
import forum.api.java.domain.authentication.entity.AddedPasswordResetToken;
import forum.api.java.domain.authentication.entity.LoginUser;
import forum.api.java.domain.authentication.entity.NewAuthentication;
import forum.api.java.domain.authentication.entity.RequestResetPasswordLink;
import forum.api.java.interfaces.http.api.authentications.dto.request.RefreshAuthenticationRequest;
import forum.api.java.interfaces.http.api.authentications.dto.request.ResetPasswordLinkRequest;
import forum.api.java.interfaces.http.api.authentications.dto.request.UserLoginRequest;
import forum.api.java.interfaces.http.api.authentications.dto.response.RefreshAuthenticationResponse;
import forum.api.java.interfaces.http.api.authentications.dto.response.ResetPasswordLinkResponse;
import forum.api.java.interfaces.http.api.authentications.dto.response.UserLoginResponse;
import forum.api.java.interfaces.http.api.authentications.dto.response.UserLogoutResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentications")
public class AuthenticationsController {
    private final LoginUserUseCase loginUserUseCase;
    private final RefreshAuthenticationUseCase refreshAuthenticationUseCase;
    private final LogoutUserUseCase logoutUserUseCase;
    private final RequestResetPasswordLinkUseCase requestResetPasswordLinkUseCase;

    public AuthenticationsController(
            LoginUserUseCase loginUserUseCase,
            RefreshAuthenticationUseCase refreshAuthenticationUseCase,
            LogoutUserUseCase logoutUserUseCase,
            RequestResetPasswordLinkUseCase requestResetPasswordLinkUseCase
    ) {
        this.loginUserUseCase = loginUserUseCase;
        this.refreshAuthenticationUseCase = refreshAuthenticationUseCase;
        this.logoutUserUseCase = logoutUserUseCase;
        this.requestResetPasswordLinkUseCase = requestResetPasswordLinkUseCase;
    }

    @PostMapping("/login-account")
    public UserLoginResponse userLoginAccount(@RequestBody UserLoginRequest request) {
        LoginUser loginUser = new LoginUser(request.getUsername(), request.getPassword(), request.getCaptchaToken());
        NewAuthentication loggedInUser = loginUserUseCase.execute(loginUser);
        return new UserLoginResponse(loggedInUser.getAccessToken(), loggedInUser.getRefreshToken());
    }

    @PostMapping("/refresh-authentication")
    public RefreshAuthenticationResponse getRefreshAuthentication(@RequestBody RefreshAuthenticationRequest request) {
        String newAccessToken = refreshAuthenticationUseCase.execute(request.getRefreshToken());
        return new RefreshAuthenticationResponse(newAccessToken);
    }

    @PostMapping("/logout-account")
    public UserLogoutResponse userLogoutAccount(@AuthenticationPrincipal String userId) {
        logoutUserUseCase.execute(userId);
        return new UserLogoutResponse("See you!");
    }

    @PostMapping("/request-reset-password-link")
    public ResetPasswordLinkResponse requestResetPasswordLinkAction(@RequestBody ResetPasswordLinkRequest request, HttpServletRequest httpRequest) throws MessagingException {
        String ip = (String) httpRequest.getAttribute("clientIp");
        String userAgent = (String) httpRequest.getAttribute("userAgent");

        RequestResetPasswordLink requestResetPasswordLink = new RequestResetPasswordLink(request.getEmail(), ip, userAgent, request.getCaptchaToken());
        AddedPasswordResetToken addedPasswordResetToken = requestResetPasswordLinkUseCase.execute(requestResetPasswordLink);

        return new ResetPasswordLinkResponse(
                addedPasswordResetToken.getId(),
                addedPasswordResetToken.getUserId(),
                addedPasswordResetToken.getTokenHash(),
                addedPasswordResetToken.getExpiresAt(),
                addedPasswordResetToken.getUsedAt(),
                addedPasswordResetToken.getIpRequest(),
                addedPasswordResetToken.getUserAgent(),
                addedPasswordResetToken.getCreatedAt(),
                addedPasswordResetToken.getUpdatedAt(),
                addedPasswordResetToken.getDeletedAt()
        );
    }
}
