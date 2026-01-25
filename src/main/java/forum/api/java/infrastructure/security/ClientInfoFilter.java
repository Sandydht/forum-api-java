package forum.api.java.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ClientInfoFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
        String ip = extractIp(request);
        String ua = request.getHeader("User-Agent");

        request.setAttribute("clientIp", ip);
        request.setAttribute("userAgent", ua);

        filterChain.doFilter(request, response);
    }

    private String extractIp(HttpServletRequest request) {
        String ip = request.getHeader("CF-Connecting-IP");

        if (ip == null || ip.isBlank()) {
            ip = request.getHeader("X-Forwarded-For");
        }

        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip != null ? ip : request.getRemoteAddr();
    }
}
