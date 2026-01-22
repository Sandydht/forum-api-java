package forum.api.java.interfaces.http.api.threadcomments.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddThreadCommentRequest {
    @NotBlank(message = "content is required")
    private String content;
}
