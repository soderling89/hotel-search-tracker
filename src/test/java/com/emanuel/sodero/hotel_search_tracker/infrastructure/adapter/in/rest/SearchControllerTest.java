package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest;

import com.emanuel.sodero.hotel_search_tracker.application.port.in.GetSearchCountUseCase;
import com.emanuel.sodero.hotel_search_tracker.application.port.in.RegisterSearchUseCase;
import com.emanuel.sodero.hotel_search_tracker.domain.model.Search;
import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchCountView;
import com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto.SearchCountResponseDto;
import com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto.SearchIdResponseDto;
import com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto.SearchRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchControllerTest {

    @Mock
    private RegisterSearchUseCase registerSearchUseCase;

    @Mock
    private GetSearchCountUseCase getSearchCountUseCase;

    @Mock
    private SearchRequestMapper searchRequestMapper;

    @InjectMocks
    private SearchController controller;

    @Test
    void shouldSubmitSearchAndReturnSearchId() {
        final SearchRequestDto request = new SearchRequestDto(
                "hotel-1",
                LocalDate.of(2026, 4, 10),
                LocalDate.of(2026, 4, 12),
                List.of(30, 29)
        );
        final Search search = new Search(
                "hotel-1",
                LocalDate.of(2026, 4, 10),
                LocalDate.of(2026, 4, 12),
                List.of(30, 29)
        );

        when(searchRequestMapper.toSearch(request)).thenReturn(search);
        when(registerSearchUseCase.submit(search)).thenReturn("search-123");

        final SearchIdResponseDto response = controller.submitSearch(request);

        assertThat(response.searchId()).isEqualTo("search-123");
    }

    @Test
    void shouldGetSearchCountAndMapResponse() {
        final SearchCountView searchCountView = new SearchCountView(
                "search-123",
                new Search("hotel-1", LocalDate.of(2026, 4, 10), LocalDate.of(2026, 4, 12), List.of(30, 29)),
                4L
        );
        final SearchCountResponseDto expectedResponse = new SearchCountResponseDto(
                "search-123",
                new SearchRequestDto(
                        "hotel-1",
                        LocalDate.of(2026, 4, 10),
                        LocalDate.of(2026, 4, 12),
                        List.of(30, 29)
                ),
                4L
        );

        when(getSearchCountUseCase.getBySearchId("search-123")).thenReturn(searchCountView);
        when(searchRequestMapper.toResponse(searchCountView)).thenReturn(expectedResponse);

        final SearchCountResponseDto response = controller.getSearchCount("search-123");

        assertThat(response).isEqualTo(expectedResponse);
    }
}