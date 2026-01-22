package forum.api.java.infrastructure.persistence.threadcomments.mapper;

import forum.api.java.domain.threadcomment.entity.AddedThreadComment;
import forum.api.java.infrastructure.persistence.threadcomments.entity.ThreadCommentJpaEntity;

import java.util.Optional;

public class ThreadCommentJpaMapper {
    public static AddedThreadComment toAddedThreadCommentDomain(ThreadCommentJpaEntity threadCommentJpaEntity) {
        return new AddedThreadComment(
                threadCommentJpaEntity.getId(),
                threadCommentJpaEntity.getContent(),
                threadCommentJpaEntity.getCreatedAt(),
                threadCommentJpaEntity.getUpdatedAt(),
                Optional.ofNullable(threadCommentJpaEntity.getDeletedAt()),
                threadCommentJpaEntity.getUser().getId(),
                threadCommentJpaEntity.getThread().getId()
        );
    }
}
