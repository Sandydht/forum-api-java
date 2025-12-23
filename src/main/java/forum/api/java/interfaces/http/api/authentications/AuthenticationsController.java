package forum.api.java.interfaces.http.api.authentications;

import forum.api.java.applications.usecase.LoginUserUseCase;
import forum.api.java.domain.authentication.entity.NewAuth;
import forum.api.java.domain.user.entity.UserLogin;
import forum.api.java.interfaces.http.api.authentications.dto.UserLoginRequest;
import forum.api.java.interfaces.http.api.authentications.dto.UserLoginResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentications")
public class AuthenticationsController {
    private final LoginUserUseCase loginUserUseCase;

    public AuthenticationsController(LoginUserUseCase loginUserUseCase) {
        this.loginUserUseCase = loginUserUseCase;
    }

    @PostMapping("login-account")
    public UserLoginResponse userLoginAccount(@RequestBody UserLoginRequest userLoginRequest) {
        UserLogin userLogin = new UserLogin(userLoginRequest.getUsername(), userLoginRequest.getPassword());
        NewAuth newAuth = loginUserUseCase.execute(userLogin);
        return new UserLoginResponse(newAuth.getAccessToken(), newAuth.getRefreshToken());
    }
}
