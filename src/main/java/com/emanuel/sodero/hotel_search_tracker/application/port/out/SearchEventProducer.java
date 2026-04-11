package com.emanuel.sodero.hotel_search_tracker.application.port.out;

import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchEvent;

public interface SearchEventProducer {

    void send(SearchEvent searchEvent);
}
