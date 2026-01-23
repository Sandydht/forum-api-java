package forum.api.java.interfaces.http.api.users.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterResponse {
    private String id;
    private String username;
    private String email;
    private String phoneNumber;
    private String fullname;
}
