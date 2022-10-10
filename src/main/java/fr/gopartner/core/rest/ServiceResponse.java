package fr.gopartner.core.rest;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ServiceResponse {

    private LocalDateTime timeStump;
    private String message;

    public ServiceResponse(LocalDateTime timeStump, String message) {
        this.timeStump = timeStump;
        this.message = message;
    }
}
