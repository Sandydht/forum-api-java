package forum.api.java.infrastructure.persistence.threads;

import forum.api.java.infrastructure.persistence.threads.entity.ThreadJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadJpaRepository extends JpaRepository<ThreadJpaEntity, String> {
    Page<ThreadJpaEntity> findByTitleContaining(String title, Pageable pageable);
}
