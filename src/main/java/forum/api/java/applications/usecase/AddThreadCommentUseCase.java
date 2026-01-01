package forum.api.java.applications.usecase;

import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.threadcomment.ThreadCommentRepository;
import forum.api.java.domain.threadcomment.entity.AddedThreadComment;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.UserThreadDetail;

public class AddThreadCommentUseCase {
    private final UserRepository userRepository;
    private final ThreadRepository threadRepository;
    private final ThreadCommentRepository threadCommentRepository;

    public AddThreadCommentUseCase(UserRepository userRepository, ThreadRepository threadRepository, ThreadCommentRepository threadCommentRepository) {
        this.userRepository = userRepository;
        this.threadRepository = threadRepository;
        this.threadCommentRepository = threadCommentRepository;
    }

    public AddedThreadComment execute(String userId, String threadId, String content) {
        return new AddedThreadComment("", "", new UserThreadDetail("", ""));
    }
}
