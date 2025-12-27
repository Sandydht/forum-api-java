package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.ClientException;
import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.entity.RegisteredUser;
import forum.api.java.domain.user.entity.UserDetail;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
@Rollback
@Import(UserRepositoryImpl.class)
@DisplayName("UserRepositoryImpl")
public class UserRepositoryImplTest {
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Nested
    @DisplayName("verifyAvailableUsername function")
    public class VerifyAvailableUsernameFunction {
        @Test
        @DisplayName("should throw ClientException when username available")
        public void testReturnTrueWhenUsernameAvailable() {
            String username = "user";
            String password = "password";
            String fullname = "Fullname";

            UserEntity userEntity = new UserEntity(username, fullname, password);
            userJpaRepository.save(userEntity);

            ClientException verifyAvailableUsernameError = Assertions.assertThrows(
                    ClientException.class,
                    () -> userRepositoryImpl.verifyAvailableUsername(username)
            );

            Assertions.assertEquals("CLIENT_EXCEPTION.USER_ALREADY_EXIST", verifyAvailableUsernameError.getMessage());
        }

        @Test
        @DisplayName("should not throw ClientException when username not available")
        public void testReturnFalseWhenUsernameNotAvailable() {
            String username = "user";
            Assertions.assertDoesNotThrow(() -> userRepositoryImpl.verifyAvailableUsername(username));
        }
    }

    @Nested
    @DisplayName("addUser function")
    public class AddUserFunction {
        @Test
        @DisplayName("should persist register user and return registered user correctly")
        public void testPersistRegisterUserAndReturnRegisteredUserCorrectly() {
            String username = "user";
            String password = "password";
            String fullname = "Fullname";

            RegisterUser registerUser = new RegisterUser(username, fullname, password);
            RegisteredUser registeredUser = userRepositoryImpl.addUser(registerUser);

            Assertions.assertNotNull(registeredUser.getId());
            Assertions.assertEquals(username, registeredUser.getUsername());
            Assertions.assertEquals(fullname, registeredUser.getFullname());
        }
    }

    @Nested
    @DisplayName("getUserById function")
    public class GetUserByIdFunction {
        @Test
        @DisplayName("should throw NotFoundException when user not found")
        public void testUserNotFound() {
            String userId = "user-id";

            NotFoundException exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> userRepositoryImpl.getUserById(userId)
            );

            Assertions.assertEquals("NOT_FOUND_EXCEPTION.USER_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should return user details correctly when user exists")
        public void testUserExists() {
            UserEntity userEntity = new UserEntity("user", "Fullname", "password");
            UserEntity savedUser = userJpaRepository.save(userEntity);
            String id = savedUser.getId();

            UserDetail result = userRepositoryImpl.getUserById(id).orElseThrow();

            Assertions.assertNotNull(result);
            Assertions.assertEquals(id, result.getId());
            Assertions.assertEquals("user", result.getUsername());
            Assertions.assertEquals("Fullname", result.getFullname());
        }
    }
}
