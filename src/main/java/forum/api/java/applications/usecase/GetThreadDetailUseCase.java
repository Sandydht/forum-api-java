package forum.api.java.applications.usecase;

import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.thread.entity.ThreadDetail;

public class GetThreadDetailUseCase {
    private final ThreadRepository threadRepository;

    public GetThreadDetailUseCase(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public ThreadDetail execute(String id) {
        return threadRepository.getThreadById(id);
    }
}
