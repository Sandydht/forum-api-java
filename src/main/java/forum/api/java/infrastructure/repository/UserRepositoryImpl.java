package forum.api.java.infrastructure.repository;

import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.infrastructure.database.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.UUID;

public class UserRepositoryImpl implements UserRepository {
    private final ConnectionUtil connectionUtil;
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    public UserRepositoryImpl(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    @Override
    public void addUser(RegisterUser registerUser) {
        System.out.println(registerUser.getUsername());
        System.out.println(registerUser.getFullname());
        System.out.println(registerUser.getPassword());

        String sql = "INSERT INTO users (id, username, fullname, password) VALUES (?, ?, ?, ?)";
        try (Connection connection = connectionUtil.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, UUID.randomUUID().toString());
                statement.setString(2, registerUser.getUsername());
                statement.setString(3, registerUser.getFullname());
                statement.setString(4, registerUser.getPassword());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    logger.info("User {} data has been saved successfully", registerUser.getUsername());
                }
            }
        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public boolean verifyAvailableUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection connection = connectionUtil.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        if (count > 0) {
                            logger.info("User {} is already exist", username);
                            return true;
                        }
                    }
                }
            }

            logger.info("User {} does not exist", username);
            return false;
        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
    }
}
