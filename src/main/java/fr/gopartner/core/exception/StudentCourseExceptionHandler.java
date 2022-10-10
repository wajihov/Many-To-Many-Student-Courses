package fr.gopartner.core.exception;

import fr.gopartner.core.rest.ServiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class StudentCourseExceptionHandler {

    @ExceptionHandler(StudentCourseException.class)
    public ResponseEntity<ServiceResponse> handlerException(StudentCourseException ex) {
        ServiceResponse serviceResponse = ServiceResponse.builder()
                .timeStump(LocalDateTime.now())
                .message(ex.getCodes().getMessage())
                .build();
        return new ResponseEntity<>(serviceResponse, ex.getCodes().getHttpStatus());
    }
}
