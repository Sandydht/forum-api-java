package forum.api.java.interfaces.http.api.authentications.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResponse {
    private String accessToken;
    private String refreshToken;
}
