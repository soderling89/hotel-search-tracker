package com.emanuel.sodero.hotel_search_tracker.application.port.in;

import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchEvent;

public interface PersistSearchUseCase {

    void store(SearchEvent searchEvent);
}
