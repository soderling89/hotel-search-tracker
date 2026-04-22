package com.emanuel.sodero.hotel_search_tracker.application.service;

import com.emanuel.sodero.hotel_search_tracker.application.port.in.GetSearchCountUseCase;
import com.emanuel.sodero.hotel_search_tracker.domain.port.out.SearchHistoryRepository;
import com.emanuel.sodero.hotel_search_tracker.domain.exception.SearchNotFoundException;
import com.emanuel.sodero.hotel_search_tracker.domain.model.Search;
import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchCountView;

public class SearchStatsService implements GetSearchCountUseCase {

    private final SearchHistoryRepository searchHistoryRepository;

    public SearchStatsService(final SearchHistoryRepository searchHistoryRepository) {
        this.searchHistoryRepository = searchHistoryRepository;
    }

    @Override
    public SearchCountView getBySearchId(final String searchId) {
        final Search search = searchHistoryRepository.findBySearchId(searchId)
                .orElseThrow(() -> new SearchNotFoundException(searchId));
        return new SearchCountView(searchId, search, searchHistoryRepository.countMatches(search));
    }
}
