package com.emanuel.sodero.hotel_search_tracker.domain.port.out;

import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchEvent;

public interface SearchEventProducer {

    void send(SearchEvent searchEvent);
}
