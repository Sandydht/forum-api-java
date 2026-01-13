package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.commons.models.PagedSearchResult;
import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.thread.entity.AddThread;
import forum.api.java.domain.thread.entity.AddedThread;
import forum.api.java.domain.thread.entity.ThreadDetail;
import forum.api.java.domain.thread.entity.UpdateThread;
import forum.api.java.infrastructure.persistence.threads.ThreadJpaRepository;
import forum.api.java.infrastructure.persistence.threads.entity.ThreadJpaEntity;
import forum.api.java.infrastructure.persistence.threads.mapper.ThreadJpaMapper;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public class ThreadRepositoryImpl implements ThreadRepository {
    private final UserJpaRepository userJpaRepository;
    private final ThreadJpaRepository threadJpaRepository;

    public ThreadRepositoryImpl(UserJpaRepository userJpaRepository, ThreadJpaRepository threadJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.threadJpaRepository = threadJpaRepository;
    }

    @Override
    public AddedThread addThread(AddThread addThread) {
        UserJpaEntity userJpaEntity = userJpaRepository.findById(addThread.getUserId())
                .orElseThrow(() -> new NotFoundException("THREAD_REPOSITORY_IMPL.USER_NOT_FOUND"));

        ThreadJpaEntity threadJpaEntity = new ThreadJpaEntity(userJpaEntity, addThread.getTitle(), addThread.getBody());
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

    @Override
    public PagedSearchResult<ThreadDetail> getThreadPaginationList(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<ThreadJpaEntity> result = threadJpaRepository.findByTitleContaining(title, pageable);
        return ThreadJpaMapper.toPagedThreadDetailDomain(result);
    }

    @Override
    public ThreadDetail updateThreadById(UpdateThread updateThread) {
        ThreadJpaEntity threadJpaEntity = threadJpaRepository
                .findById(updateThread.getThreadId())
                .orElseThrow(() -> new NotFoundException("THREAD_REPOSITORY_IMPL.THREAD_NOT_FOUND"));

        threadJpaEntity.setTitle(updateThread.getTitle());
        threadJpaEntity.setBody(updateThread.getBody());
        threadJpaEntity.setUpdatedAt(Instant.now());
        threadJpaRepository.save(threadJpaEntity);

        return ThreadJpaMapper.toThreadDetailDomain(threadJpaEntity);
    }
}
