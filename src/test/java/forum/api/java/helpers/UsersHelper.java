package forum.api.java.helpers;

import com.zaxxer.hikari.HikariDataSource;
import forum.api.java.helpers.models.UsersHelperModel;
import forum.api.java.infrastructure.database.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class UsersHelper {
    private final HikariDataSource connectionUtil = ConnectionUtil.getDataSource();
    private static final Logger logger = LoggerFactory.getLogger(UsersHelper.class);

    public void truncateUserTable() {
        try (Connection connection = connectionUtil.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("SET FOREIGN_KEY_CHECKS = 0");
            statement.execute("TRUNCATE TABLE refresh_tokens");
            statement.execute("TRUNCATE TABLE users");
            statement.execute("SET FOREIGN_KEY_CHECKS = 1");

            logger.info("User table truncated successfully");
        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
    }

    public Optional<UsersHelperModel> findUserByUsername(String username) {
        String sql = "SELECT id, username, fullname, password FROM users WHERE username = ?";
        try (Connection connection = connectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    logger.info("User {} found", username);

                    UsersHelperModel user = new UsersHelperModel(
                            UUID.fromString(resultSet.getString("id")),
                            resultSet.getString("username"),
                            resultSet.getString("fullname"),
                            resultSet.getString("password")
                    );

                    return Optional.of(user);
                }

                logger.info("User {} not found", username);
                return Optional.empty();
            }
        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
    }

    public void addUserData(String username, String fullname, String password) {
        String sql = "INSERT INTO users (id, username, fullname, password) VALUES (?, ?, ?, ?)";
        try (Connection connection = connectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, UUID.randomUUID().toString());
            statement.setString(2, username);
            statement.setString(3, fullname);
            statement.setString(4, password);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("User {} data has been saved successfully", username);
            }
        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
    }
}
