package com.emanuel.sodero.hotel_search_tracker.application.service;

import com.emanuel.sodero.hotel_search_tracker.application.port.out.SearchHistoryRepository;
import com.emanuel.sodero.hotel_search_tracker.domain.exception.SearchNotFoundException;
import com.emanuel.sodero.hotel_search_tracker.domain.model.Search;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchStatsServiceTest {

    @Mock
    private SearchHistoryRepository searchHistoryRepository;

    @InjectMocks
    private SearchStatsService service;

    @Test
    void shouldReturnCountForExistingSearch() {
        final Search search = new Search(
                "hotel-1",
                LocalDate.of(2026, 4, 10),
                LocalDate.of(2026, 4, 12),
                List.of(30, 29)
        );

        when(searchHistoryRepository.findBySearchId("abc")).thenReturn(Optional.of(search));
        when(searchHistoryRepository.countMatches(search)).thenReturn(4L);

        final var result = service.getBySearchId("abc");

        assertAll("service getBySearchId result",
                () -> assertThat(result.searchId()).isEqualTo("abc"),
                () -> assertThat(result.count()).isEqualTo(4L),
                () -> assertThat(result.search()).isEqualTo(search)
        );
    }

    @Test
    void shouldFailWhenSearchDoesNotExist() {
        when(searchHistoryRepository.findBySearchId("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getBySearchId("missing"))
                .isInstanceOf(SearchNotFoundException.class)
                .hasMessageContaining("missing");
    }
}