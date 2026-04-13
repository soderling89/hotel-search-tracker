package com.emanuel.sodero.hotel_search_tracker.domain.exception;

public class SearchEventPublishException extends RuntimeException {

    public SearchEventPublishException(final String message, final Throwable cause) {
        super(message, cause);
    }
}