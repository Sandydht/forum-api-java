package forum.api.java.interfaces.http.api;

import forum.api.java.applications.usecase.RegisterUserUseCase;
import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.entity.RegisteredUser;
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

    @PostMapping("/register")
    public RegisteredUser create(@RequestBody RegisterUser registerUser) {
        return registerUserUseCase.execute(registerUser);
    }
}
