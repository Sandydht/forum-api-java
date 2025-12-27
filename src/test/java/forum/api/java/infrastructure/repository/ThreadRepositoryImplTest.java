package forum.api.java.infrastructure.repository;

import forum.api.java.domain.thread.entity.AddThread;
import forum.api.java.domain.thread.entity.AddedThread;
import forum.api.java.domain.user.entity.UserDetail;
import forum.api.java.infrastructure.persistence.threads.ThreadJpaRepository;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
@Rollback
@Import(ThreadRepositoryImpl.class)
@DisplayName("ThreadRepositoryImpl")
public class ThreadRepositoryImplTest {
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ThreadJpaRepository threadJpaRepository;

    @Autowired
    private ThreadRepositoryImpl threadRepositoryImpl;

    @Nested
    @DisplayName("addThread function")
    public class AddThreadFunction {
        @Test
        @DisplayName("should persist add thread and return added thread correctly")
        public void testPersistAddThreadAndReturnAddedThreadCorrectly() {
            String username = "user";
            String fullname = "Fullname";
            String password = "password";

            UserEntity userEntity = new UserEntity(username, fullname, password);
            UserEntity savedUser = userJpaRepository.save(userEntity);

            String title = "Title";
            String body = "Body";

            UserDetail userDetail = new UserDetail(
                    savedUser.getId(),
                    savedUser.getUsername(),
                    savedUser.getFullname(),
                    savedUser.getPassword()
            );
            AddThread addThread = new AddThread(title, body);

            AddedThread addedThread = threadRepositoryImpl.addThread(userDetail, addThread);

            Assertions.assertNotNull(addedThread.getId());
            Assertions.assertEquals(title, addedThread.getTitle());
            Assertions.assertEquals(body, addedThread.getBody());
        }
    }
}
