package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.ClientException;
import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.domain.user.entity.UserEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
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
public class UserEntityRepositoryImplTest {
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

            UserJpaEntity userJpaEntity = new UserJpaEntity(username, fullname, password);
            userJpaRepository.save(userJpaEntity);

            ClientException verifyAvailableUsernameError = Assertions.assertThrows(
                    ClientException.class,
                    () -> userRepositoryImpl.verifyAvailableUsername(username)
            );

            Assertions.assertEquals("USER_ALREADY_EXIST", verifyAvailableUsernameError.getMessage());
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
    public class AddUserEntityFunction {
        @Test
        @DisplayName("should persist register user and return registered user correctly")
        public void testPersistRegisterUserAndReturnRegisteredUserCorrectly() {
            String username = "user";
            String fullname = "Fullname";
            String password = "password";

            UserEntity userEntity = userRepositoryImpl.addUser(username, fullname, password);

            Assertions.assertNotNull(userEntity.getId());
            Assertions.assertEquals(username, userEntity.getUsername());
            Assertions.assertEquals(fullname, userEntity.getFullname());
            Assertions.assertEquals(password, userEntity.getPassword());
        }
    }

    @Nested
    @DisplayName("getUserByUsername function")
    public class GetUserEntityByUsernameFunction {
        @Test
        @DisplayName("should throw NotFoundException when user not found")
        public void testUserNotFound() {
            String username = "user";

            NotFoundException exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> userRepositoryImpl.getUserByUsername(username)
            );

            Assertions.assertEquals("USER_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should return user details correctly when user exists")
        public void testUserExists() {
            String username = "user";
            String fullname = "Fullname";
            String password = "password";

            UserJpaEntity userJpaEntity = new UserJpaEntity(username, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            UserEntity result = userRepositoryImpl.getUserByUsername(username).orElseThrow();

            Assertions.assertEquals(savedUser.getId(), result.getId());
            Assertions.assertEquals(savedUser.getUsername(), result.getUsername());
            Assertions.assertEquals(savedUser.getFullname(), result.getFullname());
            Assertions.assertEquals(savedUser.getPassword(), result.getPassword());
        }
    }

    @Nested
    @DisplayName("getUserById function")
    public class GetUserEntityByIdFunction {
        @Test
        @DisplayName("should throw NotFoundException when user not found")
        public void testUserNotFound() {
            String userId = "user-id";

            NotFoundException exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> userRepositoryImpl.getUserById(userId)
            );

            Assertions.assertEquals("USER_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should return user details correctly when user exists")
        public void testUserExists() {
            String username = "user";
            String fullname = "Fullname";
            String password = "password";

            UserJpaEntity userJpaEntity = new UserJpaEntity(username, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            UserEntity result = userRepositoryImpl.getUserByUsername(username).orElseThrow();

            Assertions.assertEquals(savedUser.getId(), result.getId());
            Assertions.assertEquals(savedUser.getUsername(), result.getUsername());
            Assertions.assertEquals(savedUser.getFullname(), result.getFullname());
            Assertions.assertEquals(savedUser.getPassword(), result.getPassword());
        }
    }
}
