package com.products.demo.utils;

import com.products.demo.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomResponse> badRequestHandler(BadRequestException bre) {
        LOGGER.error("badRequestHandler - message {}", bre.getMessage());
        CustomResponse cr = new CustomResponse(LocalDateTime.now(), (HttpStatus.BAD_REQUEST), bre.getMessage());
        return new ResponseEntity<>(cr, cr.getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomResponse> notFoundExceptionHandler(NotFoundException nfe) {
        LOGGER.error("notFoundExceptionHandler - message: {}", nfe.getMessage());
        CustomResponse cr = new CustomResponse(LocalDateTime.now(), (HttpStatus.NOT_FOUND), nfe.getMessage());
        return new ResponseEntity<>(cr, cr.getStatus());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CustomResponse {

        private final LocalDateTime date;

        private final HttpStatus status;

        private final String message;

    }
}
