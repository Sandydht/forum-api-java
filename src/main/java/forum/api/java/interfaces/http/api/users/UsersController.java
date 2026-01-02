package forum.api.java.interfaces.http.api.users;

import forum.api.java.applications.usecase.RegisterUserUseCase;
import forum.api.java.domain.user.entity.UserEntity;
import forum.api.java.interfaces.http.api.users.dto.request.UserRegisterRequest;
import forum.api.java.interfaces.http.api.users.dto.response.UserRegisterResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final RegisterUserUseCase registerUserUseCase;

    public UsersController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("register-account")
    public UserRegisterResponse userRegistrationAccount(@RequestBody UserRegisterRequest userRegisterRequest) {
        UserEntity userEntity = registerUserUseCase.execute(userRegisterRequest.getUsername(), userRegisterRequest.getFullname(), userRegisterRequest.getPassword());
        return new UserRegisterResponse(userEntity.getId(), userEntity.getUsername(), userEntity.getFullname());
    }
}
