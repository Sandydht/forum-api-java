package forum.api.java.infrastructure.config;

import forum.api.java.commons.exceptions.DomainErrorTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionConfig {
    @Bean
    public DomainErrorTranslator domainErrorTranslator() {
        return new DomainErrorTranslator();
    }
}
