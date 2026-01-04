package forum.api.java.interfaces.http.api.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserThreadDetailResponse {
    private String id;
    private String fullname;
}
