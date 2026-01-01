package forum.api.java.infrastructure.persistence.threads;

import forum.api.java.infrastructure.persistence.threads.entity.ThreadJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadJpaRepository extends JpaRepository<ThreadJpaEntity, String> {
}
