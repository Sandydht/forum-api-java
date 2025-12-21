package forum.api.java;

import forum.api.java.applications.usecase.RegisterUserUseCase;
import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.entity.RegisteredUser;
import forum.api.java.infrastructure.repository.UserRepositoryImpl;
import forum.api.java.infrastructure.security.PasswordHashImpl;

public class App {
    public static void main(String[] args) {
        UserRepositoryImpl userRepositoryImpl = new UserRepositoryImpl();
        PasswordHashImpl passwordHashImpl = new PasswordHashImpl();

        RegisterUserUseCase registerUserUseCase = new RegisterUserUseCase(userRepositoryImpl, passwordHashImpl);
        RegisterUser registerUser = new RegisterUser("user", "password", "Fullname");

        RegisteredUser registeredUser = registerUserUseCase.execute(registerUser);

        System.out.println(registeredUser.getId());
        System.out.println(registeredUser.getUsername());
        System.out.println(registeredUser.getFullname());
    }
}
