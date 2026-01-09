package forum.api.java.infrastructure.persistence.threads.mapper;

import forum.api.java.domain.thread.entity.AddedThread;
import forum.api.java.domain.thread.entity.ThreadEntity;
import forum.api.java.infrastructure.persistence.threads.entity.ThreadJpaEntity;

public class ThreadJpaMapper {
    public static ThreadEntity toThreadEntityDomain(ThreadJpaEntity threadJpaEntity) {
        return new ThreadEntity(
                threadJpaEntity.getId(),
                threadJpaEntity.getUser().getId(),
                threadJpaEntity.getTitle(),
                threadJpaEntity.getBody()
        );
    }

    public static AddedThread toAddedThreadDomain(ThreadJpaEntity threadJpaEntity) {
        return new AddedThread(
                threadJpaEntity.getId(),
                threadJpaEntity.getTitle(),
                threadJpaEntity.getBody()
        );
    }
}
