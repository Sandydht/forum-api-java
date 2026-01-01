package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.domain.thread.entity.ThreadEntity;
import forum.api.java.infrastructure.persistence.threads.ThreadJpaRepository;
import forum.api.java.infrastructure.persistence.threads.entity.ThreadJpaEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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
        public void shouldPersistAddThreadAndReturnAddedThreadCorrectly() {
            String username = "user";
            String fullname = "Fullname";
            String password = "password";

            UserJpaEntity userJpaEntity = new UserJpaEntity(username, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            String title = "Title";
            String body = "Body";

            ThreadEntity threadEntity = threadRepositoryImpl.addThread(savedUser.getId(), title, body);

            Assertions.assertNotNull(threadEntity.getId());
            Assertions.assertEquals(title, threadEntity.getTitle());
            Assertions.assertEquals(body, threadEntity.getBody());
        }
    }

    @Nested
    @DisplayName("getThreadById function")
    public class GetThreadByIdFunction {
        @Test
        @DisplayName("should throw NotFoundException when thread not found")
        public void shouldThrowNotFoundExceptionWhenThreadNotFound() {
            String id = UUID.randomUUID().toString();

            NotFoundException exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> threadRepositoryImpl.getThreadById(id)
            );
            Assertions.assertEquals("THREAD_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should not throw NotFoundException when thread is found")
        public void shouldNotThrowNotFoundExceptionWhenThreadIsFound() {
            String username = "user";
            String fullname = "Fullname";
            String password = "password";

            UserJpaEntity userJpaEntity = new UserJpaEntity(username, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            String title = "Title";
            String body = "Body";

            ThreadJpaEntity threadJpaEntity = new ThreadJpaEntity(savedUser, title, body);
            ThreadJpaEntity savedThread = threadJpaRepository.save(threadJpaEntity);

            ThreadEntity threadEntity = threadRepositoryImpl.getThreadById(savedThread.getId());

            Assertions.assertEquals(savedThread.getId(), threadEntity.getId());
            Assertions.assertEquals(savedThread.getUser().getId(), threadEntity.getUser().getId());
            Assertions.assertEquals(savedThread.getUser().getUsername(), threadEntity.getUser().getUsername());
            Assertions.assertEquals(savedThread.getUser().getFullname(), threadEntity.getUser().getFullname());
            Assertions.assertEquals(savedThread.getUser().getPassword(), threadEntity.getUser().getPassword());
            Assertions.assertEquals(title, threadEntity.getTitle());
            Assertions.assertEquals(body, threadEntity.getBody());
        }
    }
}
