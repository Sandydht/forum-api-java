package forum.api.java.interfaces.http.api.threadcomments.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddThreadCommentResponse {
    private String id;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
}
