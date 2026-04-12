package com.emanuel.sodero.hotel_search_tracker.application.port.out;

import com.emanuel.sodero.hotel_search_tracker.domain.model.Search;

import java.util.Optional;

public interface SearchHistoryRepository {

    void store(String searchId, Search search);

    Optional<Search> findBySearchId(String searchId);

    long countMatches(Search search);
}
