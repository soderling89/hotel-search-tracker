package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SearchCountResponseDtoTest {

    @Test
    void shouldExposeAllFields() {
        final SearchRequestDto search = new SearchRequestDto(
                "hotel-1",
                LocalDate.of(2026, 4, 10),
                LocalDate.of(2026, 4, 12),
                List.of(30, 29)
        );

        final SearchCountResponseDto dto = new SearchCountResponseDto("search-123", search, 4L);

        assertThat(dto.searchId()).isEqualTo("search-123");
        assertThat(dto.search()).isEqualTo(search);
        assertThat(dto.count()).isEqualTo(4L);
    }
}