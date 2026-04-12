package com.emanuel.sodero.hotel_search_tracker.application.service;

import com.emanuel.sodero.hotel_search_tracker.application.port.out.SearchHistoryRepository;
import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchEvent;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SearchStorageServiceTest {

    @Test
    void shouldDelegatePersistenceToRepository() {
        final SearchHistoryRepository searchHistoryRepository = mock(SearchHistoryRepository.class);
        final SearchStorageService service = new SearchStorageService(searchHistoryRepository);
        final SearchEvent searchEvent = new SearchEvent(
                "search-1",
                "hotel-1",
                LocalDate.of(2026, 4, 10),
                LocalDate.of(2026, 4, 11),
                List.of(30, 1)
        );

        service.store(searchEvent);

        verify(searchHistoryRepository).store("search-1", searchEvent.toSearch());
    }
}
