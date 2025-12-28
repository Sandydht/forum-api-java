package forum.api.java.interfaces.http.api.authentications.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshAuthenticationResponse {
    private String accessToken;
}
