package forum.api.java.applications.usecase;

import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.threadcomment.ThreadCommentRepository;
import forum.api.java.domain.threadcomment.entity.AddThreadComment;
import forum.api.java.domain.threadcomment.entity.AddedThreadComment;
import forum.api.java.domain.user.UserRepository;

public class AddThreadCommentUseCase {
    private final ThreadRepository threadRepository;
    private final UserRepository userRepository;
    private final ThreadCommentRepository threadCommentRepository;

    public AddThreadCommentUseCase(ThreadRepository threadRepository, UserRepository userRepository, ThreadCommentRepository threadCommentRepository) {
        this.threadRepository = threadRepository;
        this.userRepository = userRepository;
        this.threadCommentRepository = threadCommentRepository;
    }

    public AddedThreadComment execute(AddThreadComment addThreadComment) {
        userRepository.checkAvailableUserById(addThreadComment.getUserId());
        threadRepository.checkAvailableThreadById(addThreadComment.getThreadId());
        return threadCommentRepository.addThreadComment(addThreadComment);
    }
}
