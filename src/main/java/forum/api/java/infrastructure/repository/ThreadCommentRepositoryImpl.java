package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.domain.threadcomment.ThreadCommentRepository;
import forum.api.java.domain.threadcomment.entity.AddThreadComment;
import forum.api.java.domain.threadcomment.entity.AddedThreadComment;
import forum.api.java.infrastructure.persistence.threadcomments.ThreadCommentJpaRepository;
import forum.api.java.infrastructure.persistence.threadcomments.entity.ThreadCommentJpaEntity;
import forum.api.java.infrastructure.persistence.threadcomments.mapper.ThreadCommentJpaMapper;
import forum.api.java.infrastructure.persistence.threads.ThreadJpaRepository;
import forum.api.java.infrastructure.persistence.threads.entity.ThreadJpaEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ThreadCommentRepositoryImpl implements ThreadCommentRepository {
    private final UserJpaRepository userJpaRepository;
    private final ThreadJpaRepository threadJpaRepository;
    private final ThreadCommentJpaRepository threadCommentJpaRepository;

    public ThreadCommentRepositoryImpl(UserJpaRepository userJpaRepository, ThreadJpaRepository threadJpaRepository, ThreadCommentJpaRepository threadCommentJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.threadJpaRepository = threadJpaRepository;
        this.threadCommentJpaRepository = threadCommentJpaRepository;
    }

    @Override
    public AddedThreadComment addThreadComment(AddThreadComment addThreadComment) {
        UserJpaEntity userJpaEntity = userJpaRepository.findById(addThreadComment.getUserId())
                .orElseThrow(() -> new NotFoundException("THREAD_COMMENT_REPOSITORY_IMPL.USER_NOT_FOUND"));

        ThreadJpaEntity threadJpaEntity = threadJpaRepository.findById(addThreadComment.getThreadId())
                .orElseThrow(() -> new NotFoundException("THREAD_COMMENT_REPOSITORY_IMPL.THREAD_NOT_FOUND"));

        ThreadCommentJpaEntity threadCommentJpaEntity = new ThreadCommentJpaEntity(userJpaEntity, threadJpaEntity, addThreadComment.getContent());
        ThreadCommentJpaEntity savedThreadComment = threadCommentJpaRepository.save(threadCommentJpaEntity);

        return ThreadCommentJpaMapper.toAddedThreadCommentDomain(savedThreadComment);
    }
}
