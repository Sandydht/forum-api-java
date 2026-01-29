package forum.api.java.interfaces.http.api.authentications;

import forum.api.java.applications.usecase.*;
import forum.api.java.domain.authentication.entity.LoginUser;
import forum.api.java.domain.authentication.entity.NewAuthentication;
import forum.api.java.domain.authentication.entity.RequestResetPasswordLink;
import forum.api.java.domain.authentication.entity.ResendPasswordResetToken;
import forum.api.java.interfaces.http.api.authentications.dto.request.*;
import forum.api.java.interfaces.http.api.authentications.dto.response.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/authentications")
public class AuthenticationsController {
    private final LoginUserUseCase loginUserUseCase;
    private final RefreshAuthenticationUseCase refreshAuthenticationUseCase;
    private final LogoutUserUseCase logoutUserUseCase;
    private final RequestResetPasswordLinkUseCase requestResetPasswordLinkUseCase;
    private final ResendPasswordResetTokenUseCase resendPasswordResetTokenUseCase;
    private final ValidatePasswordResetTokenUseCase validatePasswordResetTokenUseCase;

    public AuthenticationsController(
            LoginUserUseCase loginUserUseCase,
            RefreshAuthenticationUseCase refreshAuthenticationUseCase,
            LogoutUserUseCase logoutUserUseCase,
            RequestResetPasswordLinkUseCase requestResetPasswordLinkUseCase,
            ResendPasswordResetTokenUseCase resendPasswordResetTokenUseCase,
            ValidatePasswordResetTokenUseCase validatePasswordResetTokenUseCase
    ) {
        this.loginUserUseCase = loginUserUseCase;
        this.refreshAuthenticationUseCase = refreshAuthenticationUseCase;
        this.logoutUserUseCase = logoutUserUseCase;
        this.requestResetPasswordLinkUseCase = requestResetPasswordLinkUseCase;
        this.resendPasswordResetTokenUseCase = resendPasswordResetTokenUseCase;
        this.validatePasswordResetTokenUseCase = validatePasswordResetTokenUseCase;
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
    public ResetPasswordLinkResponse requestResetPasswordLinkAction(@RequestBody ResetPasswordLinkRequest request, HttpServletRequest httpRequest) {
        String ip = (String) httpRequest.getAttribute("clientIp");
        String userAgent = (String) httpRequest.getAttribute("userAgent");

        RequestResetPasswordLink requestResetPasswordLink = new RequestResetPasswordLink(request.getEmail(), ip, userAgent, request.getCaptchaToken());
        requestResetPasswordLinkUseCase.execute(requestResetPasswordLink);

        return new ResetPasswordLinkResponse("If the email is registered, we will send password reset instructions");
    }

    @PostMapping("/resend-password-reset-token")
    public RequestedNewPasswordResetTokenResponse resendPasswordResetTokenAction(
            @RequestBody ResendPasswordResetTokenRequest request,
            HttpServletRequest httpRequest
    ) throws NoSuchAlgorithmException, InvalidKeyException {
        String ip = (String) httpRequest.getAttribute("clientIp");
        String userAgent = (String) httpRequest.getAttribute("userAgent");

        ResendPasswordResetToken resendPasswordResetToken = new ResendPasswordResetToken(
                request.getToken(),
                ip,
                userAgent
        );
        resendPasswordResetTokenUseCase.execute(resendPasswordResetToken);

        return new RequestedNewPasswordResetTokenResponse("If the email is registered, we will send password reset instructions");
    }

    @PostMapping("/validate-password-reset-token")
    public void validatePasswordResetTokenAction(@RequestBody ValidatePasswordResetTokenRequest request) throws NoSuchAlgorithmException, InvalidKeyException {
        validatePasswordResetTokenUseCase.execute(request.getToken());
    }
}
