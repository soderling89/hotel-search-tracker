package com.emanuel.sodero.hotel_search_tracker.application.service;

import com.emanuel.sodero.hotel_search_tracker.application.port.in.PersistSearchUseCase;
import com.emanuel.sodero.hotel_search_tracker.application.port.out.SearchHistoryRepository;
import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchStorageService implements PersistSearchUseCase {

    private final SearchHistoryRepository searchHistoryRepository;

    public SearchStorageService(final SearchHistoryRepository searchHistoryRepository) {
        this.searchHistoryRepository = searchHistoryRepository;
    }

    @Override
    @Transactional
    public void store(final SearchEvent searchEvent) {
        searchHistoryRepository.store(searchEvent.searchId(), searchEvent.toSearch());
    }
}
