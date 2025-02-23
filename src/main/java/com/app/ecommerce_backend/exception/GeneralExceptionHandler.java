package com.app.ecommerce_backend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String ERROR_CODE = "errorCode";
    private static final String PATH = "path";
    private static final String MESSAGE = "message";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> validationErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .toList();
        log.error("MethodArgumentNotValidException: {}", ex.getMessage());
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Invalid request", request, validationErrors);
    }

    /**
     * Handles ResourceNotFoundException.
     * Used when a requested resource is not found in the system.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        log.error("ResourceNotFoundException: {}", ex.getMessage());
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
    }

    /**
     * Handles InvalidRequestException.
     * Used for invalid requests that violate business rules or input constraints.
     */

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> handleInvalidRequest(InvalidRequestException ex, WebRequest request) {
        Map<String, Object> responseBody = buildResponseBody(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
        responseBody.put(ERROR_CODE, ex.getErrorCode());
        log.error("InvalidRequestException: {}", ex.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all uncaught exceptions, providing a generic error response.
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        HttpStatus status = determineHttpStatus(ex);
        log.error("Internal Server Error: {}", ex.getMessage());
        return buildResponseEntity(status, ex.getMessage(), request, null);
    }

    /**
     * Builds a standardized response entity for exceptions.
     */

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String message, WebRequest request, List<String> errors) {
        Map<String, Object> body = buildResponseBody(status, message, request);
        if (errors != null) {
            body.put(ERROR, errors);
        }
        return new ResponseEntity<>(body, status);
    }

    /**
     * Constructs the response body for exceptions.
     */

    private Map<String, Object> buildResponseBody(HttpStatus status, String message, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, Instant.now());
        body.put(STATUS, status.value());
        body.put(PATH, request.getDescription(false).replace("uri=", ""));
        body.put(MESSAGE, message != null ? message : status.getReasonPhrase());
        return body;
    }

    /**
     * Determines the HTTP status for uncaught exceptions.
     * Checks if the exception has a @ResponseStatus annotation.
     */

    private HttpStatus determineHttpStatus(Exception ex) {
        ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
        return responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * Formats field errors for validation exceptions.
     */

    private String formatFieldError(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }

}
