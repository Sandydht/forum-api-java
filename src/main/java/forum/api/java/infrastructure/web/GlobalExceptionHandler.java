package forum.api.java.infrastructure.web;

import forum.api.java.commons.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final DomainErrorTranslator domainErrorTranslator;

    public GlobalExceptionHandler(DomainErrorTranslator domainErrorTranslator) {
        this.domainErrorTranslator = domainErrorTranslator;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception exception) {
        RuntimeException translatedMessage = domainErrorTranslator.translate(exception);

        if (translatedMessage instanceof ClientException clientException) {
            int statusCode = clientException.getStatusCode();
            ErrorResponse error = new ErrorResponse(statusCode, translatedMessage.getMessage());
            return ResponseEntity
                    .status(statusCode)
                    .body(error);
        }

        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), translatedMessage.getMessage());
        return ResponseEntity.status(500)
                .body(error);
    }
}
