package forum.api.java.interfaces.http.api.threads.dto;

import forum.api.java.domain.user.entity.UserThreadDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetThreadDetailResponse {
    private String id;
    private String title;
    private String body;
    private UserThreadDetail userThreadDetail;
}
