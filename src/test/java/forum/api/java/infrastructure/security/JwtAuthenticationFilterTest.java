package forum.api.java.infrastructure.security;

import forum.api.java.applications.security.AuthenticationTokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@DisplayName("JwtAuthenticationFilterTest")
@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationFilterTest {
    @Mock
    private AuthenticationTokenManager authenticationTokenManager;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("should set authentication when token is valid")
    public void testSetAuthenticationWhenTokenIsValid() throws ServletException, IOException {
        String token = "valid-token";
        String userId = "user-123";

        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        Mockito.when(authenticationTokenManager.decodeJWTPayload(token)).thenReturn(userId);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        Assertions.assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        Assertions.assertEquals(userId, SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        Mockito.verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("should not set authentication when header is missing")
    public void shouldNotSetAuthenticationWhenHeaderIsMissing() throws ServletException, IOException {
        Mockito.when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());

        Mockito.verify(filterChain).doFilter(request, response);
        Mockito.verifyNoInteractions(authenticationTokenManager);
    }

    @Test
    @DisplayName("should clear context when token is invalid or exception occurs")
    public void shouldClearContectWhenTokenIsInvalid() throws ServletException, IOException {
        String token = "invalid-token";

        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        Mockito.when(authenticationTokenManager.decodeJWTPayload(token)).thenThrow(new RuntimeException("Invalid token"));

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());

        Mockito.verify(filterChain).doFilter(request, response);
    }
}
