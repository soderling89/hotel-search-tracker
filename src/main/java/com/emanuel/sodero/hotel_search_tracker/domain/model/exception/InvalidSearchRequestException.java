package com.emanuel.sodero.hotel_search_tracker.domain.model.exception;

public class InvalidSearchRequestException extends RuntimeException {

    public InvalidSearchRequestException(final String message) {
        super(message);
    }
}
