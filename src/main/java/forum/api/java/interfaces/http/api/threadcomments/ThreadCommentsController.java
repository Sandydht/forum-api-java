package forum.api.java.interfaces.http.api.threadcomments;

import forum.api.java.applications.usecase.AddThreadCommentUseCase;
import forum.api.java.domain.threadcomment.entity.AddThreadComment;
import forum.api.java.domain.threadcomment.entity.AddedThreadComment;
import forum.api.java.interfaces.http.api.threadcomments.dto.request.AddThreadCommentRequest;
import forum.api.java.interfaces.http.api.threadcomments.dto.response.AddThreadCommentResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/thread-comments")
public class ThreadCommentsController {
    private final AddThreadCommentUseCase addThreadCommentUseCase;

    public ThreadCommentsController(AddThreadCommentUseCase addThreadCommentUseCase) {
        this.addThreadCommentUseCase = addThreadCommentUseCase;
    }

    @PostMapping("/add-comment/{thread-id}")
    public AddThreadCommentResponse addThreadCommentAction(@AuthenticationPrincipal String userId, @PathVariable("thread-id") String threadId, @RequestBody AddThreadCommentRequest addThreadCommentRequest) {
        AddThreadComment addThreadComment = new AddThreadComment(userId, threadId, addThreadCommentRequest.getContent());
        AddedThreadComment result = addThreadCommentUseCase.execute(addThreadComment);
        return new AddThreadCommentResponse(
                result.getId(),
                result.getContent(),
                result.getCreatedAt(),
                result.getUpdatedAt()
        );
    }
}
