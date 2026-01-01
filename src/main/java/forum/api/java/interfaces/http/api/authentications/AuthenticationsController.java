package forum.api.java.interfaces.http.api.authentications;

import forum.api.java.applications.usecase.LoginUserUseCase;
import forum.api.java.applications.usecase.LogoutUserUseCase;
import forum.api.java.applications.usecase.RefreshAuthenticationUseCase;
import forum.api.java.interfaces.http.api.authentications.dto.*;
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

    public AuthenticationsController(LoginUserUseCase loginUserUseCase, RefreshAuthenticationUseCase refreshAuthenticationUseCase, LogoutUserUseCase logoutUserUseCase) {
        this.loginUserUseCase = loginUserUseCase;
        this.refreshAuthenticationUseCase = refreshAuthenticationUseCase;
        this.logoutUserUseCase = logoutUserUseCase;
    }

    @PostMapping("login-account")
    public UserLoginResponse userLoginAccount(@RequestBody UserLoginRequest request) {
        String accessToken = loginUserUseCase.execute(request.getUsername(), request.getPassword());
        return new UserLoginResponse(accessToken);
    }

    @PostMapping("refresh-authentication")
    public RefreshAuthenticationResponse getRefreshAuthentication(@RequestBody RefreshAuthenticationRequest request) {
        String newAccessToken = refreshAuthenticationUseCase.execute(request.getRefreshToken());
        return new RefreshAuthenticationResponse(newAccessToken);
    }

    @PostMapping("logout-account")
    public UserLogoutResponse userLogoutAccount(@RequestBody UserLogoutRequest request) {
        logoutUserUseCase.execute(request.getRefreshToken());
        return new UserLogoutResponse("See you!");
    }
}
