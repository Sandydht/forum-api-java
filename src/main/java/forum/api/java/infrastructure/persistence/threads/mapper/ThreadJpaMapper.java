package forum.api.java.infrastructure.persistence.threads.mapper;

import forum.api.java.domain.thread.entity.AddedThread;
import forum.api.java.domain.thread.entity.ThreadDetail;
import forum.api.java.domain.user.entity.UserThreadDetail;
import forum.api.java.infrastructure.persistence.threads.entity.ThreadJpaEntity;

public class ThreadJpaMapper {
    public static AddedThread toAddedThreadDomain(ThreadJpaEntity threadJpaEntity) {
        return new AddedThread(
                threadJpaEntity.getId(),
                threadJpaEntity.getTitle(),
                threadJpaEntity.getBody()
        );
    }

    public static ThreadDetail toThreadDetailDomain(ThreadJpaEntity threadJpaEntity) {
        UserThreadDetail userThreadDetail = new UserThreadDetail(
                threadJpaEntity.getUser().getId(),
                threadJpaEntity.getUser().getUsername(),
                threadJpaEntity.getUser().getFullname()
        );

        return new ThreadDetail(
                threadJpaEntity.getId(),
                threadJpaEntity.getTitle(),
                threadJpaEntity.getBody(),
                userThreadDetail
        );
    }
}
