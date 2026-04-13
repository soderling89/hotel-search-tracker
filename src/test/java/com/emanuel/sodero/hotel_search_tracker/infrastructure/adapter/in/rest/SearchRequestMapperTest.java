package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest;

import com.emanuel.sodero.hotel_search_tracker.domain.model.Search;
import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchCountView;
import com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto.SearchRequestDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SearchRequestMapperTest {

    private final SearchRequestMapper mapper = new SearchRequestMapper();

    @Test
    void shouldMapRequestToDomainAndPreserveImmutability() {
        final List<Integer> ages = new ArrayList<>(List.of(30, 29));
        final SearchRequestDto dto = new SearchRequestDto(
                "hotel-1",
                LocalDate.of(2026, 4, 10),
                LocalDate.of(2026, 4, 12),
                ages
        );

        ages.add(1);
        final Search mapped = mapper.toSearch(dto);

        assertThat(mapped.ages()).containsExactly(30, 29);
    }

    @Test
    void shouldMapCountResultToResponse() {
        final Search search = new Search("hotel-1", LocalDate.of(2026, 4, 10), LocalDate.of(2026, 4, 12), List.of(3, 29, 30, 1));
        final SearchCountView result = new SearchCountView("id-1", search, 100L);

        final var response = mapper.toResponse(result);

        assertThat(response.searchId()).isEqualTo("id-1");
        assertThat(response.count()).isEqualTo(100L);
        assertThat(response.search().ages()).containsExactly(3, 29, 30, 1);
    }
}
