package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.commons.models.PagedSearchResult;
import forum.api.java.domain.thread.entity.AddThread;
import forum.api.java.domain.thread.entity.AddedThread;
import forum.api.java.domain.thread.entity.ThreadDetail;
import forum.api.java.domain.thread.entity.UpdateThread;
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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@DataJpaTest
@Transactional
@Rollback
@ActiveProfiles("test")
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
        @DisplayName("should throw NotFoundException when user not found")
        public void shouldThrowNotFoundExceptionWhenUserNotFound() {
            String userId = UUID.randomUUID().toString();
            String title = "Title";
            String body = "Body";

            AddThread addThread = new AddThread(userId, title, body);

            NotFoundException exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> threadRepositoryImpl.addThread(addThread)
            );
            Assertions.assertEquals("THREAD_REPOSITORY_IMPL.USER_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should persist add thread and return added thread correctly")
        public void shouldPersistAddThreadAndReturnAddedThreadCorrectly() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = "6281123123123";
            String fullname = "Fullname";
            String password = "password";

            UserJpaEntity userJpaEntity = new UserJpaEntity(null, username, email, phoneNumber, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            String title = "Title";
            String body = "Body";

            AddThread addThread = new AddThread(savedUser.getId(), title, body);
            AddedThread addedThread = threadRepositoryImpl.addThread(addThread);

            Assertions.assertNotNull(addedThread.getId());
            Assertions.assertEquals(title, addedThread.getTitle());
            Assertions.assertEquals(body, addedThread.getBody());
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
            Assertions.assertEquals("THREAD_REPOSITORY_IMPL.THREAD_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should not throw NotFoundException when thread is found")
        public void shouldNotThrowNotFoundExceptionWhenThreadIsFound() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = "6281123123123";
            String fullname = "Fullname";
            String password = "password";

            UserJpaEntity userJpaEntity = new UserJpaEntity(null, username, email, phoneNumber, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            String title = "Title";
            String body = "Body";

            ThreadJpaEntity threadJpaEntity = new ThreadJpaEntity(savedUser, title, body);
            ThreadJpaEntity savedThread = threadJpaRepository.save(threadJpaEntity);

            ThreadDetail threadDetail = threadRepositoryImpl.getThreadById(savedThread.getId());

            Assertions.assertEquals(savedThread.getId(), threadDetail.getId());
            Assertions.assertEquals(title, threadDetail.getTitle());
            Assertions.assertEquals(body, threadDetail.getBody());
            Assertions.assertNotNull(threadDetail.getCreatedAt());
            Assertions.assertNotNull(threadDetail.getUpdatedAt());
            Assertions.assertEquals(savedUser.getId(), threadDetail.getOwner().getId());
            Assertions.assertEquals(savedUser.getUsername(), threadDetail.getOwner().getUsername());
            Assertions.assertEquals(savedUser.getFullname(), threadDetail.getOwner().getFullname());
        }
    }

    @Nested
    @DisplayName("getThreadPaginationList function")
    public class GetThreadPaginationListFunction {
        @Test
        @DisplayName("should return paged threads correctly based on title search and pagination")
        public void shouldReturnPagedThreadsCorrectlyBasedOnTitleSearchAndPagination() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = "6281123123123";
            String fullname = "Fullname";
            String password = "password";
            UserJpaEntity user = userJpaRepository.save(new UserJpaEntity(null, username, email, phoneNumber, fullname, password));

            threadJpaRepository.save(new ThreadJpaEntity(user, "Belajar Spring Boot", "Konten Spring"));
            threadJpaRepository.save(new ThreadJpaEntity(user, "Belajar Java Dasar", "Konten Java"));
            threadJpaRepository.save(new ThreadJpaEntity(user, "Tutorial Microservices", "Konten Microservices"));
            threadJpaRepository.save(new ThreadJpaEntity(user, "Spring Security Guide", "Konten Security"));

            String searchTitle = "Tutorial";
            int page = 0;
            int size = 10;

            PagedSearchResult<ThreadDetail> result = threadRepositoryImpl.getThreadPaginationList(searchTitle, page, size);

            Assertions.assertEquals(1, result.getData().size());
            Assertions.assertEquals(page, result.getPage());
            Assertions.assertEquals(size, result.getSize());
            Assertions.assertEquals(1, result.getTotalPages());
            Assertions.assertEquals(1, result.getTotalElements());

            Assertions.assertTrue(result.getData().get(0).getTitle().contains(searchTitle));
            Assertions.assertEquals(username, result.getData().get(0).getOwner().getUsername());
        }

        @Test
        @DisplayName("should return empty data when title does not match any thread")
        public void shouldReturnEmptyDataWhenTitleNoMatch() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = "6281123123123";
            String fullname = "Fullname";
            String password = "password";
            UserJpaEntity user = userJpaRepository.save(new UserJpaEntity(null, username, email, phoneNumber, fullname, password));

            threadJpaRepository.save(new ThreadJpaEntity(user, "Thread A", "Body A"));

            String searchTitle = "NonExistent";
            int page = 0;
            int size = 10;
            PagedSearchResult<ThreadDetail> result = threadRepositoryImpl.getThreadPaginationList(searchTitle, page, size);

            Assertions.assertEquals(0, result.getData().size());
            Assertions.assertEquals(0, result.getTotalElements());
            Assertions.assertEquals(0, result.getTotalPages());
        }

        @Test
        @DisplayName("should handle pagination correctly when data exceeds page size")
        public void shouldHandlePaginationCorrectly() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = "6281123123123";
            String fullname = "Fullname";
            String password = "password";
            UserJpaEntity user = userJpaRepository.save(new UserJpaEntity(null, username, email, phoneNumber, fullname, password));

            for (int i = 0; i < 3; i++) {
                threadJpaRepository.save(new ThreadJpaEntity(user, "Java Thread " + i, "Body"));
            }

            PagedSearchResult<ThreadDetail> firstPage = threadRepositoryImpl.getThreadPaginationList("Java", 0, 2);
            PagedSearchResult<ThreadDetail> secondPage = threadRepositoryImpl.getThreadPaginationList("Java", 1, 2);

            Assertions.assertEquals(2, firstPage.getData().size());
            Assertions.assertEquals(1, secondPage.getData().size()); // Sisa 1 data di halaman kedua
            Assertions.assertEquals(3, firstPage.getTotalElements());
            Assertions.assertEquals(2, firstPage.getTotalPages());
        }
    }

    @Nested
    @DisplayName("updateThreadById function")
    public class UpdateThreadByIdFunction {
        @Test
        @DisplayName("should throw NotFoundException when thread not found")
        public void shouldThrowNotFoundExceptionWhenThreadNotFound() {
            String id = UUID.randomUUID().toString();
            String title = "New title";
            String body = "New body";
            UpdateThread updateThread = new UpdateThread(id, title, body);

            NotFoundException exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> threadRepositoryImpl.updateThreadById(updateThread)
            );
            Assertions.assertEquals("THREAD_REPOSITORY_IMPL.THREAD_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should update thread and return updated thread correctly")
        public void shouldUpdateThreadAndReturnUpdatedThreadCorrectly() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = "6281123123123";
            String fullname = "Fullname";
            String password = "password";

            UserJpaEntity userJpaEntity = new UserJpaEntity(null, username, email, phoneNumber, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            String title = "Title";
            String body = "Body";

            ThreadJpaEntity threadJpaEntity = new ThreadJpaEntity(savedUser, title, body);
            threadJpaEntity.setCreatedAt(LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0)
                    .toInstant(ZoneOffset.UTC));
            ThreadJpaEntity savedThread = threadJpaRepository.save(threadJpaEntity);

            String newTitle = "New title";
            String newBody = "New body";
            UpdateThread updateThread = new UpdateThread(savedThread.getId(), newTitle, newBody);

            ThreadDetail result = threadRepositoryImpl.updateThreadById(updateThread);

            Assertions.assertEquals(savedThread.getId(), result.getId());
            Assertions.assertEquals(newTitle, result.getTitle());
            Assertions.assertEquals(newBody, result.getBody());
            Assertions.assertTrue(result.getUpdatedAt().isAfter(savedThread.getCreatedAt()) || result.getUpdatedAt().equals(savedThread.getCreatedAt()));
            Assertions.assertEquals(savedUser.getId(), result.getOwner().getId());
            Assertions.assertEquals(savedUser.getUsername(), result.getOwner().getUsername());
            Assertions.assertEquals(savedUser.getFullname(), result.getOwner().getFullname());
        }
    }

    @Nested
    @DisplayName("deleteThreadById function")
    public class DeleteThreadByIdFunction {
        @Test
        @DisplayName("should throw NotFoundException when thread not found")
        public void shouldThrowNotFoundExceptionWhenThreadNotFound() {
            String id = UUID.randomUUID().toString();

            NotFoundException exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> threadRepositoryImpl.deleteThreadById(id)
            );
            Assertions.assertEquals("THREAD_REPOSITORY_IMPL.THREAD_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should soft delete thread and return thread data correctly")
        public void shouldSoftDeleteThreadAndReturnThreadDataCorrectly() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = "6281123123123";
            String fullname = "Fullname";
            String password = "password";

            UserJpaEntity userJpaEntity = new UserJpaEntity(null, username, email, phoneNumber, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            String title = "Title";
            String body = "Body";

            ThreadJpaEntity threadJpaEntity = new ThreadJpaEntity(savedUser, title, body);
            ThreadJpaEntity savedThread = threadJpaRepository.save(threadJpaEntity);

            Assertions.assertNull(savedThread.getDeletedAt()); // when not doing soft delete

            ThreadDetail result = threadRepositoryImpl.deleteThreadById(savedThread.getId());

            Assertions.assertEquals(savedThread.getId(), result.getId());
            Assertions.assertNotNull(result.getDeletedAt());
        }
    }

    @Nested
    @DisplayName("checkAvailableThreadById function")
    public class CheckAvailableThreadByIdFunction {
        @Test
        @DisplayName("should throw NotFoundException when thread not found")
        public void shouldThrowNotFoundExceptionWhenThreadNotFound() {
            String id = UUID.randomUUID().toString();

            NotFoundException exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> threadRepositoryImpl.checkAvailableThreadById(id)
            );

            Assertions.assertEquals("THREAD_REPOSITORY_IMPL.THREAD_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should not throw NotFoundException when thread is found")
        public void shouldNotThrowNotFoundExceptionWhenThreadIsFound() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = "6281123123123";
            String fullname = "Fullname";
            String password = "password";

            UserJpaEntity userJpaEntity = new UserJpaEntity(null, username, email, phoneNumber, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            String title = "Title";
            String body = "Body";

            ThreadJpaEntity threadJpaEntity = new ThreadJpaEntity(savedUser, title, body);
            ThreadJpaEntity savedThread = threadJpaRepository.save(threadJpaEntity);

            Assertions.assertDoesNotThrow(() -> threadRepositoryImpl.checkAvailableThreadById(savedThread.getId()));
        }
    }
}