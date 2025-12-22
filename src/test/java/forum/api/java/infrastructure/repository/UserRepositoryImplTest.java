package forum.api.java.infrastructure.repository;

import com.zaxxer.hikari.HikariDataSource;
import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.entity.RegisteredUser;
import forum.api.java.helpers.UsersHelper;
import forum.api.java.helpers.models.UsersHelperModel;
import forum.api.java.infrastructure.database.ConnectionUtil;
import org.junit.jupiter.api.*;

import java.util.Optional;

@DisplayName("UserRepositoryImpl")
public class UserRepositoryImplTest {
    private UserRepositoryImpl userRepositoryImpl;
    private final UsersHelper usersHelper = new UsersHelper();
    private final HikariDataSource connectionUtil = ConnectionUtil.getDataSource();

    @BeforeEach
    public void setUp() {
        usersHelper.truncateUserTable();
        userRepositoryImpl = new UserRepositoryImpl(connectionUtil);
    }

    @AfterAll
    public static void tearDown() {
        ConnectionUtil.getDataSource().close();
    }

    @Test
    @DisplayName("should return true when username available")
    public void testReturnTrueWhenUsernameAvailable() {
        String username = "user";
        String password = "password";
        String fullname = "Fullname";

        usersHelper.addUserData(username, fullname, password);
        boolean isAvailable = userRepositoryImpl.verifyAvailableUsername(username);

        Assertions.assertTrue(isAvailable);
    }

    @Test
    @DisplayName("should false when username not available")
    public void testReturnFalseWhenUsernameNotAvailable() {
        String username = "user";

        boolean isAvailable = userRepositoryImpl.verifyAvailableUsername(username);

        Assertions.assertFalse(isAvailable);
    }

    @Test
    @DisplayName("should persist register user and return registered user correctly")
    public void testPersistRegisterUserAndReturnRegisteredUserCorrectly() {
        String username = "user";
        String password = "password";
        String fullname = "Fullname";

        RegisterUser registerUser = new RegisterUser(username, fullname, password);
        userRepositoryImpl.addUser(registerUser)
                .ifPresent(registeredUser -> {
                    Assertions.assertNotNull(registeredUser.getId());
                    Assertions.assertEquals(username, registeredUser.getUsername());
                    Assertions.assertEquals(fullname, registeredUser.getFullname());
                });
    }
}
