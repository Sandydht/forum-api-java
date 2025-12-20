package forum.api.java.domain.user;

import forum.api.java.domain.user.entity.RegisterUser;

public interface UserRepository {
    RegisterUser addUser(RegisterUser registerUser);
    boolean verifyAvailableUsername(String username);
}
