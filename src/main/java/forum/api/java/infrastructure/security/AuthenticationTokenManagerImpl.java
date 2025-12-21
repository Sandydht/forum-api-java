package forum.api.java.infrastructure.security;

import forum.api.java.applications.security.AuthenticationTokenManager;

import java.util.UUID;

public class AuthenticationTokenManagerImpl implements AuthenticationTokenManager {
    @Override
    public String createAccessToken(UUID id) {
        return "";
    }

    @Override
    public String createRefreshToken(UUID id) {
        return "";
    }
}
