package forum.api.java.applications.usecase;

import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.thread.entity.ThreadDetail;

public class DeleteThreadUseCase {
    private final ThreadRepository threadRepository;

    public DeleteThreadUseCase(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public ThreadDetail execute(String id) {
        return threadRepository.deleteThreadById(id);
    }
}
