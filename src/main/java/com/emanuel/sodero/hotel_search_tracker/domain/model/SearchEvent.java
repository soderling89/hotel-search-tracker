package com.emanuel.sodero.hotel_search_tracker.domain.model;

import java.time.LocalDate;
import java.util.List;

public record SearchEvent(
        String searchId,
        String hotelId,
        LocalDate checkIn,
        LocalDate checkOut,
        List<Integer> ages
) {
    public SearchEvent {
        ages = List.copyOf(ages);
    }

    public Search toSearch() {
        return new Search(hotelId, checkIn, checkOut, ages);
    }

}
