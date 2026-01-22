package forum.api.java.infrastructure.persistence.threadcomments;

import forum.api.java.infrastructure.persistence.threadcomments.entity.ThreadCommentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadCommentJpaRepository extends JpaRepository<ThreadCommentJpaEntity, String> {

}
