package forum.api.java.interfaces.http.api.users;

import forum.api.java.applications.usecase.GetUserProfileUseCase;
import forum.api.java.applications.usecase.RegisterUserUseCase;
import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.entity.RegisteredUser;
import forum.api.java.domain.user.entity.UserProfile;
import forum.api.java.interfaces.http.api.users.dto.request.UserRegisterRequest;
import forum.api.java.interfaces.http.api.users.dto.response.UserProfileResponse;
import forum.api.java.interfaces.http.api.users.dto.response.UserRegisterResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserProfileUseCase getUserProfileUseCase;

    public UsersController(
            RegisterUserUseCase registerUserUseCase,
            GetUserProfileUseCase getUserProfileUseCase
    ) {
        this.registerUserUseCase = registerUserUseCase;
        this.getUserProfileUseCase = getUserProfileUseCase;
    }

    @PostMapping("/register-account")
    public UserRegisterResponse userRegistrationAccountAction(@RequestBody UserRegisterRequest userRegisterRequest) {
        RegisterUser registerUser = new RegisterUser(
                userRegisterRequest.getUsername(),
                userRegisterRequest.getEmail(),
                userRegisterRequest.getPhoneNumber(),
                userRegisterRequest.getFullname(),
                userRegisterRequest.getPassword(),
                userRegisterRequest.getCaptchaToken()
        );
        RegisteredUser registeredUser = registerUserUseCase.execute(registerUser);
        return new UserRegisterResponse(
                registeredUser.getId(),
                registeredUser.getUsername(),
                registeredUser.getEmail(),
                registeredUser.getPhoneNumber(),
                registeredUser.getFullname()
        );
    }

    @GetMapping("/get-profile")
    public UserProfileResponse userProfileAction(@AuthenticationPrincipal String userId) {
        UserProfile result = getUserProfileUseCase.execute(userId);
        return new UserProfileResponse(result.getId(), result.getUsername(), result.getFullname());
    }
}
