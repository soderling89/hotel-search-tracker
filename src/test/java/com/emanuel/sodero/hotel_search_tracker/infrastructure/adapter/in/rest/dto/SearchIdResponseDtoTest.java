package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SearchIdResponseDtoTest {

    @Test
    void shouldExposeSearchId() {
        final SearchIdResponseDto dto = new SearchIdResponseDto("search-123");

        assertThat(dto.searchId()).isEqualTo("search-123");
    }

    @Test
    void shouldAllowNullSearchIdIfConstructedThatWay() {
        final SearchIdResponseDto dto = new SearchIdResponseDto(null);

        assertThat(dto.searchId()).isNull();
    }
}