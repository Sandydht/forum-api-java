package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.thread.entity.AddThread;
import forum.api.java.domain.thread.entity.AddedThread;
import forum.api.java.domain.thread.entity.ThreadDetail;
import forum.api.java.domain.user.entity.UserDetail;
import forum.api.java.domain.user.entity.UserThreadDetail;
import forum.api.java.infrastructure.persistence.threads.ThreadJpaRepository;
import forum.api.java.infrastructure.persistence.threads.entity.ThreadEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserEntity;
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
    public AddedThread addThread(UserDetail userDetail, AddThread addThread) {
        UserEntity user = userJpaRepository
                .findById(userDetail.getId())
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND"));

        ThreadEntity threadEntity = new ThreadEntity(user, addThread.getTitle(), addThread.getBody());
        ThreadEntity savedThread = threadJpaRepository.save(threadEntity);

        return new AddedThread(savedThread.getId(), savedThread.getTitle(), savedThread.getBody());
    }

    @Override
    public ThreadDetail getThreadById(String id) {
        ThreadEntity thread = threadJpaRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("THREAD_NOT_FOUND"));

        return new ThreadDetail(thread.getId(), thread.getTitle(), thread.getBody(), new UserThreadDetail(thread.getUser().getId(), thread.getUser().getFullname()));
    }
}
