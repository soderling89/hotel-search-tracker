package com.emanuel.sodero.hotel_search_tracker.application.port.out;

import com.emanuel.sodero.hotel_search_tracker.domain.model.Search;

public interface SearchHistoryRepository {

    void store(String searchId, Search search);
}
