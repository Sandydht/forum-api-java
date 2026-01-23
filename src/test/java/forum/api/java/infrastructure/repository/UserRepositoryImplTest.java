package forum.api.java.infrastructure.repository;

import forum.api.java.applications.service.PhoneNumberNormalizer;
import forum.api.java.commons.exceptions.InvariantException;
import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.entity.RegisteredUser;
import forum.api.java.domain.user.entity.UserDetail;
import forum.api.java.domain.user.entity.UserProfile;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
@Rollback
@ActiveProfiles("test")
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
        @DisplayName("should throw InvarianException when username available")
        public void shouldThrowInvariantExceptionWhenUsernameAvailable() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = PhoneNumberNormalizer.normalize("6281123123123");;
            String password = "password";
            String fullname = "Fullname";

            UserJpaEntity userJpaEntity = new UserJpaEntity(null, username, email, phoneNumber, fullname, password);
            userJpaRepository.save(userJpaEntity);

            InvariantException verifyAvailableUsernameError = Assertions.assertThrows(
                    InvariantException.class,
                    () -> userRepositoryImpl.verifyAvailableUsername(username)
            );

            Assertions.assertEquals("USER_REPOSITORY_IMPL.USER_ALREADY_EXIST", verifyAvailableUsernameError.getMessage());
        }

        @Test
        @DisplayName("should not throw InvarianException when username not available")
        public void shouldNotThrowInvariantExceptionWhenUsernameNotAvailable() {
            String username = "user";
            Assertions.assertDoesNotThrow(() -> userRepositoryImpl.verifyAvailableUsername(username));
        }
    }

    @Nested
    @DisplayName("addUser function")
    public class AddUserFunction {
        @Test
        @DisplayName("should persist register user and return registered user correctly")
        public void shouldPersistRegisterUserAndReturnRegisteredUserCorrectly() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = PhoneNumberNormalizer.normalize("6281123123123");;
            String fullname = "Fullname";
            String password = "password123";
            String captchaToken = "captcha-token";

            RegisterUser registerUser = new RegisterUser(username, email, phoneNumber, fullname, password, captchaToken);
            RegisteredUser registeredUser = userRepositoryImpl.addUser(registerUser);

            Assertions.assertNotNull(registeredUser.getId());
            Assertions.assertEquals(username, registeredUser.getUsername());
            Assertions.assertEquals(fullname, registeredUser.getFullname());
        }
    }

    @Nested
    @DisplayName("getUserByUsername function")
    public class GetUserByUsernameFunction {
        @Test
        @DisplayName("should throw NotFoundException when user not found")
        public void shouldThrowNotFoundExceptionWhenUserNotFound() {
            String username = "user";

            NotFoundException exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> userRepositoryImpl.getUserByUsername(username)
            );

            Assertions.assertEquals("USER_REPOSITORY_IMPL.USER_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should return user detail correctly when user exists")
        public void shouldReturnUserDetailCorrectlyWhenUserExists() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = PhoneNumberNormalizer.normalize("6281123123123");;
            String fullname = "Fullname";
            String password = "password";

            UserJpaEntity userJpaEntity = new UserJpaEntity(null, username, email, phoneNumber, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            UserDetail result = userRepositoryImpl.getUserByUsername(username);

            Assertions.assertEquals(savedUser.getId(), result.getId());
            Assertions.assertEquals(savedUser.getUsername(), result.getUsername());
            Assertions.assertEquals(savedUser.getFullname(), result.getFullname());
            Assertions.assertEquals(savedUser.getPassword(), result.getPassword());
        }
    }

    @Nested
    @DisplayName("getUserById function")
    public class GetUserByIdFunction {
        @Test
        @DisplayName("should throw NotFoundException when user not found")
        public void shouldThrowNotFoundExceptionWhenUserNotFound() {
            String userId = "user-id";

            NotFoundException exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> userRepositoryImpl.getUserById(userId)
            );

            Assertions.assertEquals("USER_REPOSITORY_IMPL.USER_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should return user detail correctly when user exists")
        public void shouldReturnUserDetailCorrectlyWhenUserExists() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = PhoneNumberNormalizer.normalize("6281123123123");;
            String fullname = "Fullname";
            String password = "password";

            UserJpaEntity userJpaEntity = new UserJpaEntity(null, username, email, phoneNumber, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            UserDetail result = userRepositoryImpl.getUserById(savedUser.getId());

            Assertions.assertEquals(savedUser.getId(), result.getId());
            Assertions.assertEquals(savedUser.getUsername(), result.getUsername());
            Assertions.assertEquals(savedUser.getFullname(), result.getFullname());
            Assertions.assertEquals(savedUser.getPassword(), result.getPassword());
        }
    }

    @Nested
    @DisplayName("getUserProfile function")
    public class GetUserProfileFunction {
        @Test
        @DisplayName("should throw NotFoundException when user profile not found")
        public void shouldThrowNotFoundExceptionWhenUserProfileNotFound() {
            String userId = "user-id";

            NotFoundException exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> userRepositoryImpl.getUserProfile(userId)
            );

            Assertions.assertEquals("USER_REPOSITORY_IMPL.USER_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should return user profile data correctly")
        public void shouldReturnUserProfileDataCorrectly() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = PhoneNumberNormalizer.normalize("6281123123123");;
            String fullname = "Fullname";
            String password = "password";

            UserJpaEntity userJpaEntity = new UserJpaEntity(null, username, email, phoneNumber, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            UserProfile result = userRepositoryImpl.getUserProfile(savedUser.getId());

            Assertions.assertEquals(savedUser.getId(), result.getId());
            Assertions.assertEquals(savedUser.getUsername(), result.getUsername());
            Assertions.assertEquals(savedUser.getFullname(), result.getFullname());
        }
    }

    @Nested
    @DisplayName("checkAvailableUserById function")
    public class CheckAvailableUserByIdFunction {
        @Test
        @DisplayName("should throw NotFoundException when user not found")
        public void shouldThrowNotFoundExceptionWhenUserNotFound() {
            String userId = "user-id";

            NotFoundException exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> userRepositoryImpl.checkAvailableUserById(userId)
            );

            Assertions.assertEquals("USER_REPOSITORY_IMPL.USER_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should not throw NotFoundException when user is found")
        public void shouldNotThrowNotFoundExceptionWhenUserIsFound() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = PhoneNumberNormalizer.normalize("6281123123123");;
            String fullname = "Fullname";
            String password = "password";

            UserJpaEntity userJpaEntity = new UserJpaEntity(null, username, email, phoneNumber, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            Assertions.assertDoesNotThrow(() -> userRepositoryImpl.checkAvailableUserById(savedUser.getId()));
        }
    }

    @Nested
    @DisplayName("verifyAvailableEmail function")
    public class VerifyAvailableEmailFunction {
        @Test
        @DisplayName("should throw InvarianException when email available")
        public void shouldThrowInvariantExceptionWhenUsernameAvailable() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = PhoneNumberNormalizer.normalize("6281123123123");;
            String password = "password";
            String fullname = "Fullname";

            UserJpaEntity userJpaEntity = new UserJpaEntity(null, username, email, phoneNumber, fullname, password);
            userJpaRepository.save(userJpaEntity);

            InvariantException verifyAvailableUsernameError = Assertions.assertThrows(
                    InvariantException.class,
                    () -> userRepositoryImpl.verifyAvailableEmail(email)
            );

            Assertions.assertEquals("USER_REPOSITORY_IMPL.EMAIL_ALREADY_EXIST", verifyAvailableUsernameError.getMessage());
        }

        @Test
        @DisplayName("should not throw InvarianException when email not available")
        public void shouldNotThrowInvariantExceptionWhenUsernameNotAvailable() {
            String email = "example@email.com";
            Assertions.assertDoesNotThrow(() -> userRepositoryImpl.verifyAvailableEmail(email));
        }
    }

    @Nested
    @DisplayName("verifyAvailablePhoneNumber function")
    public class VerifyAvailablePhoneNumberFunction {
        @Test
        @DisplayName("should throw InvarianException when phone number available")
        public void shouldThrowInvariantExceptionWhenUsernameAvailable() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = "+6281123123123";
            String password = "password";
            String fullname = "Fullname";

            UserJpaEntity userJpaEntity = new UserJpaEntity(null, username, email, phoneNumber, fullname, password);
            userJpaRepository.save(userJpaEntity);

            InvariantException verifyAvailableUsernameError = Assertions.assertThrows(
                    InvariantException.class,
                    () -> userRepositoryImpl.verifyAvailablePhoneNumber(phoneNumber)
            );

            Assertions.assertEquals("USER_REPOSITORY_IMPL.PHONE_NUMBER_ALREADY_EXIST", verifyAvailableUsernameError.getMessage());
        }

        @Test
        @DisplayName("should not throw InvarianException when phone Number not available")
        public void shouldNotThrowInvariantExceptionWhenUsernameNotAvailable() {
            String phoneNumber = "+6281123123123";
            Assertions.assertDoesNotThrow(() -> userRepositoryImpl.verifyAvailablePhoneNumber(phoneNumber));
        }
    }
}
