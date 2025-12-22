package forum.api.java.infrastructure.repository;

import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.entity.RegisteredUser;
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

    @Test
    @DisplayName("should throw IllegalStateException when username available")
    public void testReturnTrueWhenUsernameAvailable() {
        String username = "user";
        String password = "password";
        String fullname = "Fullname";

        UserEntity userEntity = new UserEntity(username, fullname, password);
        userJpaRepository.save(userEntity);

        IllegalStateException verifyAvailableUsernameError = Assertions.assertThrows(
                IllegalStateException.class,
                () -> userRepositoryImpl.verifyAvailableUsername(username)
        );

        Assertions.assertEquals("USER_REPOSITORY_IMPL.USER_ALREADY_EXIST", verifyAvailableUsernameError.getMessage());
    }

    @Test
    @DisplayName("should not throw IllegalStateException when username not available")
    public void testReturnFalseWhenUsernameNotAvailable() {
        String username = "user";
        Assertions.assertDoesNotThrow(() -> userRepositoryImpl.verifyAvailableUsername(username));
    }

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
