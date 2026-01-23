package forum.api.java.infrastructure.repository;

import forum.api.java.applications.service.PhoneNumberNormalizer;
import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.domain.threadcomment.entity.AddThreadComment;
import forum.api.java.domain.threadcomment.entity.AddedThreadComment;
import forum.api.java.infrastructure.persistence.threadcomments.ThreadCommentJpaRepository;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@DataJpaTest
@Transactional
@Rollback
@ActiveProfiles("test")
@Import(ThreadCommentRepositoryImpl.class)
@DisplayName("ThreadCommentRepositoryImpl")
public class ThreadCommentRepositoryImplTest {
    @Autowired
    private ThreadJpaRepository threadJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ThreadCommentJpaRepository threadCommentJpaRepository;

    @Autowired
    private ThreadCommentRepositoryImpl threadCommentRepositoryImpl;

    @Nested
    @DisplayName("addThreadComment function")
    public class AddThreadCommentFunction {
        @Test
        @DisplayName("should throw NotFoundException when user not found")
        public void shouldThrowNotFoundExceptionWhenUserNotFound() {
            String userId = UUID.randomUUID().toString();
            String threadId = UUID.randomUUID().toString();
            String content = "content";

            AddThreadComment addThreadComment = new AddThreadComment(userId, threadId, content);

            NotFoundException exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> threadCommentRepositoryImpl.addThreadComment(addThreadComment)
            );
            Assertions.assertEquals("THREAD_COMMENT_REPOSITORY_IMPL.USER_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should throw NotFoundException when thread not found")
        public void shouldThrowNotFoundExceptionWhenThreadNotFound() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = PhoneNumberNormalizer.normalize("6281123123123");;
            String fullname = "Fullname";
            String password = "password";

            UserJpaEntity userJpaEntity = new UserJpaEntity(null, username, email, phoneNumber, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            String threadId = UUID.randomUUID().toString();
            String content = "content";

            AddThreadComment addThreadComment = new AddThreadComment(savedUser.getId(), threadId, content);

            NotFoundException exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> threadCommentRepositoryImpl.addThreadComment(addThreadComment)
            );
            Assertions.assertEquals("THREAD_COMMENT_REPOSITORY_IMPL.THREAD_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should persist add thread comment and return added thread comment correctly")
        public void shouldPersistAddThreadCommentAndReturnAddedThreadCommentCorrectly() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = PhoneNumberNormalizer.normalize("6281123123123");;
            String fullname = "Fullname";
            String password = "password";

            UserJpaEntity userJpaEntity = new UserJpaEntity(null, username, email, phoneNumber, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            String title = "Title";
            String body = "Body";

            ThreadJpaEntity threadJpaEntity = new ThreadJpaEntity(savedUser, title, body);
            ThreadJpaEntity savedThread = threadJpaRepository.save(threadJpaEntity);

            String content = "content";

            AddThreadComment addThreadComment = new AddThreadComment(savedUser.getId(), savedThread.getId(), content);
            AddedThreadComment result = threadCommentRepositoryImpl.addThreadComment(addThreadComment);

            Assertions.assertNotNull(result.getUserId());
            Assertions.assertEquals(content, result.getContent());
            Assertions.assertEquals(content, result.getContent());
        }
    }
}
