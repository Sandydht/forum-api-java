package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.thread.entity.ThreadEntity;
import forum.api.java.domain.user.entity.UserEntity;
import forum.api.java.infrastructure.persistence.threads.ThreadJpaRepository;
import forum.api.java.infrastructure.persistence.threads.entity.ThreadJpaEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ThreadRepositoryImpl implements ThreadRepository {
    private final UserJpaRepository userJpaRepository;
    private final ThreadJpaRepository threadJpaRepository;

    public ThreadRepositoryImpl(UserJpaRepository userJpaRepository, ThreadJpaRepository threadJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.threadJpaRepository = threadJpaRepository;
    }

    @Override
    public ThreadEntity addThread(String userId, String title, String body) {
        UserJpaEntity userJpaEntity = userJpaRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND"));

        ThreadJpaEntity threadJpaEntity = new ThreadJpaEntity(userJpaEntity, title, body);
        ThreadJpaEntity savedThread = threadJpaRepository.save(threadJpaEntity);

        UserEntity userEntity = new UserEntity(userJpaEntity.getId(), userJpaEntity.getUsername(), userJpaEntity.getFullname(), userJpaEntity.getPassword());
        return new ThreadEntity(
                savedThread.getId(),
                userEntity,
                savedThread.getTitle(),
                savedThread.getBody()
        );
    }

    @Override
    public ThreadEntity getThreadById(String id) {
        return threadJpaRepository
                .findById(id)
                .map(thread -> new ThreadEntity(
                        thread.getId(),
                        new UserEntity(
                                thread.getUser().getId(),
                                thread.getUser().getUsername(),
                                thread.getUser().getFullname(),
                                thread.getUser().getPassword()
                        ),
                        thread.getTitle(),
                        thread.getBody()
                ))
                .orElseThrow(() -> new NotFoundException("THREAD_NOT_FOUND"));
    }
}
