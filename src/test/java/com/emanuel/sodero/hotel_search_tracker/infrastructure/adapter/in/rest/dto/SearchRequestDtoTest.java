package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SearchRequestDtoTest {

    @Test
    void shouldCopyAgesWhenProvided() {
        final List<Integer> ages = new ArrayList<>(List.of(30, 29));
        final SearchRequestDto dto = new SearchRequestDto(
                "hotel-1",
                LocalDate.of(2026, 4, 10),
                LocalDate.of(2026, 4, 12),
                ages
        );

        ages.add(1);

        assertThat(dto.ages()).containsExactly(30, 29);
    }

    @Test
    void shouldKeepNullAgesWhenPayloadDoesNotIncludeThem() {
        final SearchRequestDto dto = new SearchRequestDto(
                "hotel-1",
                LocalDate.of(2026, 4, 10),
                LocalDate.of(2026, 4, 12),
                null
        );

        assertThat(dto.ages()).isNull();
    }
}
