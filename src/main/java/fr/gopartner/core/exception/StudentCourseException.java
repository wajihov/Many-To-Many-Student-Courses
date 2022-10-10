package fr.gopartner.core.exception;

import fr.gopartner.core.rest.Codes;
import lombok.Getter;

@Getter
public class StudentCourseException extends RuntimeException {

    private Codes codes;

    public StudentCourseException(Codes codes) {
        super(codes.getMessage());
        this.codes = codes;
    }
}
