package forum.api.java.interfaces.http.api;

import com.zaxxer.hikari.HikariDataSource;
import forum.api.java.applications.usecase.RegisterUserUseCase;
import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.infrastructure.database.ConnectionUtil;
import forum.api.java.infrastructure.repository.UserRepositoryImpl;
import forum.api.java.infrastructure.security.PasswordHashImpl;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;

public class UsersApp {
    private static final Logger logger = LoggerFactory.getLogger(UsersApp.class);
    private static final HikariDataSource connectionUtil = ConnectionUtil.getDataSource();

    public static void main(String[] args) {
        registerUser();
    }

    public static void registerUser() {
        PasswordHashImpl passwordHashImpl = new PasswordHashImpl(new BCrypt());
        UserRepositoryImpl userRepositoryImpl = new UserRepositoryImpl(connectionUtil);
        RegisterUserUseCase registerUserUseCase = new RegisterUserUseCase(userRepositoryImpl, passwordHashImpl);

        RegisterUser registerUser = new RegisterUser("user10", "fullname", "password");
        registerUserUseCase.execute(registerUser)
                .ifPresent(registeredUser -> {
                    Gson gson = new Gson();
                    String json = gson.toJson(registeredUser);
                    logger.info("{}", json);
                });
    }
}
