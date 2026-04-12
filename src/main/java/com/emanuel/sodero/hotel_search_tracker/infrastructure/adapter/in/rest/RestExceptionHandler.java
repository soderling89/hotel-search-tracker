package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest;

import com.emanuel.sodero.hotel_search_tracker.domain.exception.InvalidSearchRequestException;
import com.emanuel.sodero.hotel_search_tracker.domain.exception.SearchNotFoundException;
import com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.OffsetDateTime;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(MethodArgumentNotValidException exception) {
        List<String> details = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    if (error instanceof FieldError fieldError) {
                        return String.format("%s: %s",
                                fieldError.getField(),
                                error.getDefaultMessage());
                    }
                    return error.getDefaultMessage();
                })
                .toList();

        return buildResponse(HttpStatus.BAD_REQUEST, details);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolation(ConstraintViolationException exception) {
        List<String> details = exception.getConstraintViolations()
                .stream()
                .map(violation -> String.format("%s: %s",
                        violation.getPropertyPath(),
                        violation.getMessage()))
                .toList();

        return buildResponse(HttpStatus.BAD_REQUEST, details);
    }

    @ExceptionHandler({
            InvalidSearchRequestException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ErrorResponseDto> handleBadRequest(Exception exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, List.of(exception.getMessage()));
    }

    @ExceptionHandler(SearchNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(SearchNotFoundException exception) {
        return buildResponse(HttpStatus.NOT_FOUND, List.of(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleUnexpected() {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                List.of("Unexpected internal error"));
    }

    private ResponseEntity<ErrorResponseDto> buildResponse(HttpStatus status, List<String> details) {
        ErrorResponseDto body = new ErrorResponseDto(
                OffsetDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                details
        );
        return ResponseEntity.status(status).body(body);
    }
}