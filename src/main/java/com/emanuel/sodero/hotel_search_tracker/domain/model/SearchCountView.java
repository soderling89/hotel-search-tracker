package com.emanuel.sodero.hotel_search_tracker.domain.model;

public record SearchCountView(
        String searchId,
        Search search,
        long count
) {
}
