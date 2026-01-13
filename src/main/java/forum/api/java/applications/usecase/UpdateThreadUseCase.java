package forum.api.java.applications.usecase;

import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.thread.entity.ThreadDetail;
import forum.api.java.domain.thread.entity.UpdateThread;

public class UpdateThreadUseCase {
    private final ThreadRepository threadRepository;

    public UpdateThreadUseCase(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public ThreadDetail execute(UpdateThread updateThread) {
        return threadRepository.updateThreadById(updateThread);
    }
}
