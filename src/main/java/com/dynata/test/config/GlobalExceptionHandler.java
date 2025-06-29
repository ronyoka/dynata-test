package com.dynata.test.config;

import com.dynata.test.exception.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler for the application.
 * This class handles exceptions thrown by the application and returns appropriate responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles ApiException and returns a response with the status and message from the exception.
     *
     * @param ex the ApiException
     * @return a ResponseEntity with the status and message from the exception
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }

    /**
     * Handles all other exceptions and returns a 500 Internal Server Error response.
     *
     * @param ex the Exception
     * @return a ResponseEntity with a 500 status and the exception message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        return ResponseEntity.internalServerError().body("An unexpected error occurred: " + ex.getMessage());
    }
}