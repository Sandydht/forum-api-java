package forum.api.java.infrastructure.repository;

import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.infrastructure.database.ConnectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;

@DisplayName("UserRepositoryImpl")
public class UserRepositoryImplTest {
    private UserRepositoryImpl userRepositoryImpl;

    @BeforeEach
    public void setUp() throws SQLException {
        String sql = "TRUNCATE TABLE users";
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(sql);
            }
        }

        userRepositoryImpl = new UserRepositoryImpl(new ConnectionUtil());
    }

    @Test
    @DisplayName("should return true when username available")
    public void testReturnTrueWhenUsernameAvailable() {
        String username = "user";
        String password = "password";
        String fullname = "Fullname";

        RegisterUser registerUser = new RegisterUser(username, fullname, password);
        userRepositoryImpl.addUser(registerUser);

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
        userRepositoryImpl.addUser(registerUser);

        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Assertions.assertEquals(username, resultSet.getString("username"));
                        Assertions.assertEquals(fullname, resultSet.getString("fullname"));
                        Assertions.assertEquals(password, resultSet.getString("password"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
