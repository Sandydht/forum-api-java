package forum.api.java.applications.usecase;

import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.thread.entity.AddThread;
import forum.api.java.domain.thread.entity.AddedThread;

public class AddThreadUseCase {
    private final ThreadRepository threadRepository;

    public AddThreadUseCase(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public AddedThread execute(AddThread addThread) {
        return threadRepository.addThread(addThread);
    }
}
