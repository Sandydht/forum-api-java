package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.thread.entity.AddedThread;
import forum.api.java.domain.thread.entity.ThreadDetail;
import forum.api.java.infrastructure.persistence.threads.ThreadJpaRepository;
import forum.api.java.infrastructure.persistence.threads.entity.ThreadJpaEntity;
import forum.api.java.infrastructure.persistence.threads.mapper.ThreadJpaMapper;
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
    public AddedThread addThread(String userId, String title, String body) {
        UserJpaEntity userJpaEntity = userJpaRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("THREAD_REPOSITORY_IMPL.USER_NOT_FOUND"));

        ThreadJpaEntity threadJpaEntity = new ThreadJpaEntity(userJpaEntity, title, body);
        ThreadJpaEntity savedThread = threadJpaRepository.save(threadJpaEntity);

        return ThreadJpaMapper.toAddedThreadDomain(savedThread);
    }

    @Override
    public ThreadDetail getThreadById(String id) {
        return threadJpaRepository
                .findById(id)
                .map(ThreadJpaMapper::toThreadDetailDomain)
                .orElseThrow(() -> new NotFoundException("THREAD_REPOSITORY_IMPL.THREAD_NOT_FOUND"));
    }
}
