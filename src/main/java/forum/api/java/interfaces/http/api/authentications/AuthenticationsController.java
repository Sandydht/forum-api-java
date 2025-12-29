package forum.api.java.interfaces.http.api.authentications;

import forum.api.java.applications.usecase.LoginUserUseCase;
import forum.api.java.applications.usecase.LogoutUserUseCase;
import forum.api.java.applications.usecase.RefreshAuthenticationUseCase;
import forum.api.java.domain.authentication.entity.LogoutAuth;
import forum.api.java.domain.authentication.entity.NewAuth;
import forum.api.java.domain.authentication.entity.RefreshAuth;
import forum.api.java.domain.user.entity.UserLogin;
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
        UserLogin userLogin = new UserLogin(request.getUsername(), request.getPassword());
        NewAuth newAuth = loginUserUseCase.execute(userLogin);
        return new UserLoginResponse(newAuth.getAccessToken(), newAuth.getRefreshToken());
    }

    @PostMapping("refresh-authentication")
    public RefreshAuthenticationResponse getRefreshAuthentication(@RequestBody RefreshAuthenticationRequest request) {
        RefreshAuth refreshAuth = new RefreshAuth(request.getRefreshToken());
        String newAccessToken = refreshAuthenticationUseCase.execute(refreshAuth);
        return new RefreshAuthenticationResponse(newAccessToken);
    }

    @PostMapping("logout-account")
    public UserLogoutResponse userLogoutAccount(@RequestBody UserLogoutRequest request) {
        LogoutAuth logoutAuth = new LogoutAuth(request.getRefreshToken());
        logoutUserUseCase.execute(logoutAuth);
        return new UserLogoutResponse("See you!");
    }
}
