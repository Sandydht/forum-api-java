package forum.api.java.infrastructure.config;

import com.auth0.jwt.algorithms.Algorithm;
import forum.api.java.infrastructure.properties.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {
    @Bean
    public Algorithm jwtAlgorithm(JwtProperties jwtProperties) {
        return Algorithm.HMAC256(jwtProperties.getSecretKey());
    }
}
