package com.dynata.test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Represents an exception response to be used in an API context.
 * This class encapsulates details about the HTTP status code
 * and a user-friendly message that can be conveyed to clients.
 * <p>
 * This is typically used to standardize error responses in API communication,
 * making it easier for clients to interpret errors.
 */
@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final String message;
}
