package fr.gopartner.core.rest;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Codes {

    ERROR_STUDENT_NOT_FOUND("STUDENT NOT FOUND", HttpStatus.NOT_FOUND),
    ERROR_STUDENTS_NOT_FOUND("STUDENTS NOT FOUND", HttpStatus.NOT_FOUND),
    ERROR_COURSE_NOT_FOUND("COURSE NOT FOUND", HttpStatus.NOT_FOUND),
    ERROR_COURSES_NOT_FOUND("COURSES NOT FOUND", HttpStatus.NOT_FOUND);

    private String message;
    private HttpStatus httpStatus;

    Codes(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
