package forum.api.java.applications.usecase;

import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.thread.entity.ThreadEntity;

public class AddThreadUseCase {
    private final ThreadRepository threadRepository;

    public AddThreadUseCase(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public ThreadEntity execute(String userId, String title, String body) {
        return threadRepository.addThread(userId, title, body);
    }
}
