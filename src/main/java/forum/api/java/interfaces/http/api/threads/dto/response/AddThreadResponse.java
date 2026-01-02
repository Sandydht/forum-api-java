package forum.api.java.interfaces.http.api.threads.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddThreadResponse {
    private String id;
    private String title;
    private String body;
}
