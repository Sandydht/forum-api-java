package forum.api.java.infrastructure.repository;

import com.zaxxer.hikari.HikariDataSource;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.entity.RegisteredUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private final HikariDataSource connectionUtil;
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    public UserRepositoryImpl(HikariDataSource connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    @Override
    public Optional<RegisteredUser> addUser(RegisterUser registerUser) {
        String sql = "INSERT INTO users (id, username, fullname, password) VALUES (?, ?, ?, ?)";
        try (Connection connection = connectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, registerUser.getId().toString());
            statement.setString(2, registerUser.getUsername());
            statement.setString(3, registerUser.getFullname());
            statement.setString(4, registerUser.getPassword());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("{} data has been saved successfully", registerUser.getUsername());
                return Optional.of(
                        new RegisteredUser(
                                registerUser.getId(),
                                registerUser.getUsername(),
                                registerUser.getFullname()
                        )
                );
            }
        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }

        logger.info("Failed to save {} data", registerUser.getUsername());
        return Optional.empty();
    }

    @Override
    public boolean verifyAvailableUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection connection = connectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
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

            logger.info("User {} does not exist", username);
            return false;
        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
    }
}
