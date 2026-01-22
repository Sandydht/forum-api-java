package forum.api.java.interfaces.http.api.threads;

import forum.api.java.applications.usecase.*;
import forum.api.java.commons.models.PagedSearchResult;
import forum.api.java.domain.thread.entity.AddThread;
import forum.api.java.domain.thread.entity.AddedThread;
import forum.api.java.domain.thread.entity.ThreadDetail;
import forum.api.java.domain.thread.entity.UpdateThread;
import forum.api.java.interfaces.http.api.common.response.UserThreadDetailResponse;
import forum.api.java.interfaces.http.api.threads.dto.request.AddThreadRequest;
import forum.api.java.interfaces.http.api.threads.dto.request.UpdateThreadRequest;
import forum.api.java.interfaces.http.api.threads.dto.response.AddThreadResponse;
import forum.api.java.interfaces.http.api.threads.dto.response.GetThreadDetailResponse;
import forum.api.java.interfaces.http.api.threads.dto.response.GetThreadPaginationListResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/threads")
public class ThreadsController {
    private final AddThreadUseCase addThreadUseCase;
    private final GetThreadDetailUseCase getThreadDetailUseCase;
    private final GetThreadPaginationListUseCase getThreadPaginationListUseCase;
    private final UpdateThreadUseCase updateThreadUseCase;
    private final DeleteThreadUseCase deleteThreadUseCase;

    public ThreadsController(
            AddThreadUseCase addThreadUseCase,
            GetThreadDetailUseCase getThreadDetailUseCase,
            GetThreadPaginationListUseCase getThreadPaginationListUseCase,
            UpdateThreadUseCase updateThreadUseCase,
            DeleteThreadUseCase deleteThreadUseCase
    ) {
        this.addThreadUseCase = addThreadUseCase;
        this.getThreadDetailUseCase = getThreadDetailUseCase;
        this.getThreadPaginationListUseCase = getThreadPaginationListUseCase;
        this.updateThreadUseCase = updateThreadUseCase;
        this.deleteThreadUseCase = deleteThreadUseCase;
    }

    @PostMapping("add-thread")
    public AddThreadResponse addThreadAction(@AuthenticationPrincipal String userId, @RequestBody AddThreadRequest addThreadRequest) {
        AddThread addThread = new AddThread(userId, addThreadRequest.getTitle(), addThreadRequest.getBody());
        AddedThread addedThread = addThreadUseCase.execute(addThread);
        return new AddThreadResponse(addedThread.getId(), addedThread.getTitle(), addedThread.getBody());
    }

    @GetMapping("thread-detail/{id}")
    public GetThreadDetailResponse getThreadDetailAction(@PathVariable("id") String threadId) {
        ThreadDetail threadDetail = getThreadDetailUseCase.execute(threadId);

        UserThreadDetailResponse owner = new UserThreadDetailResponse(
                threadDetail.getOwner().getId(),
                threadDetail.getOwner().getUsername(),
                threadDetail.getOwner().getFullname()
        );

        return new GetThreadDetailResponse(
                threadDetail.getId(),
                threadDetail.getTitle(),
                threadDetail.getBody(),
                threadDetail.getCreatedAt(),
                threadDetail.getUpdatedAt(),
                threadDetail.getDeletedAt(),
                owner
        );
    }

    @GetMapping("thread-pagination-list")
    public GetThreadPaginationListResponse getThreadPaginationListAction(@RequestParam(defaultValue = "") String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PagedSearchResult<ThreadDetail> result = getThreadPaginationListUseCase.execute(title, page, size);
        List<GetThreadDetailResponse> resultData = result.getData().stream().map(thread -> new GetThreadDetailResponse(
            thread.getId(),
            thread.getTitle(),
            thread.getBody(),
            thread.getCreatedAt(),
            thread.getUpdatedAt(),
            thread.getDeletedAt(),
            new UserThreadDetailResponse(thread.getOwner().getId(), thread.getOwner().getUsername(), thread.getOwner().getFullname())
        )).toList();

        return new GetThreadPaginationListResponse(
                resultData,
                result.getPage(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    @PatchMapping("update-thread/{id}")
    public GetThreadDetailResponse updateThreadAction(@PathVariable("id") String threadId, @RequestBody UpdateThreadRequest updateThreadRequest) {
        UpdateThread updateThread = new UpdateThread(threadId, updateThreadRequest.getTitle(), updateThreadRequest.getBody());
        ThreadDetail result = updateThreadUseCase.execute(updateThread);

        UserThreadDetailResponse owner = new UserThreadDetailResponse(
                result.getOwner().getId(),
                result.getOwner().getUsername(),
                result.getOwner().getFullname()
        );

        return new GetThreadDetailResponse(
                result.getId(),
                result.getTitle(),
                result.getBody(),
                result.getCreatedAt(),
                result.getUpdatedAt(),
                result.getDeletedAt(),
                owner
        );
    }

    @DeleteMapping("delete-thread/{id}")
    public GetThreadDetailResponse deleteThreadAction(@PathVariable("id") String threadId) {
        ThreadDetail result = deleteThreadUseCase.execute(threadId);

        UserThreadDetailResponse owner = new UserThreadDetailResponse(
                result.getOwner().getId(),
                result.getOwner().getUsername(),
                result.getOwner().getFullname()
        );

        return new GetThreadDetailResponse(
                result.getId(),
                result.getTitle(),
                result.getBody(),
                result.getCreatedAt(),
                result.getUpdatedAt(),
                result.getDeletedAt(),
                owner
        );
    }
}
