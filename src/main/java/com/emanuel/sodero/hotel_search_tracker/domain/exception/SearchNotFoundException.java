package com.emanuel.sodero.hotel_search_tracker.domain.exception;

public class SearchNotFoundException extends RuntimeException {

    public SearchNotFoundException(final String searchId) {
        super(new StringBuilder().append("Search not found for id ").append(searchId).toString());
    }
}
