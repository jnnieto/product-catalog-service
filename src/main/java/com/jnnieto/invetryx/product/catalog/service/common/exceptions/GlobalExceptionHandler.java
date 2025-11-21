package com.jnnieto.invetryx.product.catalog.service.common.exceptions;

import com.jnnieto.invetryx.product.catalog.service.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiError> buildResponse(
            HttpStatus status,
            String message,
            WebRequest request
    ) {
        ApiError error = new ApiError(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, WebRequest req) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), req);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorized(UnauthorizedException ex, WebRequest req) {
        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), req);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiError> handleForbidden(ForbiddenException ex, WebRequest req) {
        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage(), req);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, WebRequest req) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(
            MethodArgumentNotValidException ex,
            WebRequest req
    ) {
        StringBuilder sb = new StringBuilder();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                sb.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ")
        );

        return buildResponse(HttpStatus.BAD_REQUEST, sb.toString(), req);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ApiError> handleConflict(ResourceConflictException ex, WebRequest req) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), req);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ApiError> handleUnprocessable(UnprocessableEntityException ex, WebRequest req) {
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), req);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception ex, WebRequest req) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), req);
    }
}

