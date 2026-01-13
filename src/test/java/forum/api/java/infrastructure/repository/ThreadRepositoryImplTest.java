package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.commons.models.PagedSearchResult;
import forum.api.java.domain.thread.entity.AddThread;
import forum.api.java.domain.thread.entity.AddedThread;
import forum.api.java.domain.thread.entity.ThreadDetail;
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
            String fullname = "Fullname";
            String password = "password";

            UserJpaEntity userJpaEntity = new UserJpaEntity(username, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            String title = "Title";
            String body = "Body";

            ThreadJpaEntity threadJpaEntity = new ThreadJpaEntity(savedUser, title, body);
            ThreadJpaEntity savedThread = threadJpaRepository.save(threadJpaEntity);

            ThreadDetail threadDetail = threadRepositoryImpl.getThreadById(savedThread.getId());

            Assertions.assertEquals(savedThread.getId(), threadDetail.getId());
            Assertions.assertEquals(title, threadDetail.getTitle());
            Assertions.assertEquals(body, threadDetail.getBody());
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
            String fullname = "Fullname";
            String password = "password";
            UserJpaEntity user = userJpaRepository.save(new UserJpaEntity(username, fullname, password));

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
            String fullname = "Fullname";
            String password = "password";
            UserJpaEntity user = userJpaRepository.save(new UserJpaEntity(username, fullname, password));

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
            String fullname = "Fullname";
            String password = "password";
            UserJpaEntity user = userJpaRepository.save(new UserJpaEntity(username, fullname, password));

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
}