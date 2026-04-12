package com.emanuel.sodero.hotel_search_tracker.application.service;

import com.emanuel.sodero.hotel_search_tracker.application.port.out.SearchHistoryRepository;
import com.emanuel.sodero.hotel_search_tracker.domain.exception.SearchNotFoundException;
import com.emanuel.sodero.hotel_search_tracker.domain.model.Search;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchStatsServiceTest {

    @Test
    void shouldReturnCountForExistingSearch() {
        final SearchHistoryRepository searchHistoryRepository = mock(SearchHistoryRepository.class);
        final SearchStatsService service = new SearchStatsService(searchHistoryRepository);
        final Search search = new Search("hotel-1", LocalDate.of(2026, 4, 10), LocalDate.of(2026, 4, 12), List.of(30, 29));

        when(searchHistoryRepository.findBySearchId("abc")).thenReturn(Optional.of(search));
        when(searchHistoryRepository.countMatches(search)).thenReturn(4L);

        final var result = service.getBySearchId("abc");

        assertThat(result.searchId()).isEqualTo("abc");
        assertThat(result.count()).isEqualTo(4L);
        assertThat(result.search()).isEqualTo(search);
    }

    @Test
    void shouldFailWhenSearchDoesNotExist() {
        final SearchHistoryRepository searchHistoryRepository = mock(SearchHistoryRepository.class);
        final SearchStatsService service = new SearchStatsService(searchHistoryRepository);

        when(searchHistoryRepository.findBySearchId("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getBySearchId("missing"))
                .isInstanceOf(SearchNotFoundException.class)
                .hasMessageContaining("missing");
    }
}
