package com.emanuel.sodero.hotel_search_tracker.application.service;

import com.emanuel.sodero.hotel_search_tracker.application.port.out.SearchEventProducer;
import com.emanuel.sodero.hotel_search_tracker.application.port.out.SearchIdGenerator;
import com.emanuel.sodero.hotel_search_tracker.domain.exception.InvalidSearchRequestException;
import com.emanuel.sodero.hotel_search_tracker.domain.exception.SearchEventPublishException;
import com.emanuel.sodero.hotel_search_tracker.domain.model.Search;
import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchSubmissionServiceTest {

    @Mock
    private SearchIdGenerator searchIdGenerator;

    @Mock
    private SearchEventProducer searchEventProducer;

    @InjectMocks
    private SearchService service;

    @Test
    void shouldGenerateIdAndPublishMessage() {
        final Search search = new Search(
                "hotel-1",
                LocalDate.of(2026, 4, 10),
                LocalDate.of(2026, 4, 12),
                List.of(30, 5)
        );

        when(searchIdGenerator.generate()).thenReturn("search-123");

        final String response = service.submit(search);

        assertThat(response).isEqualTo("search-123");
    }

    @Test
    void shouldRejectInvalidStayDates() {
        final Search search = new Search(
                "hotel-1",
                LocalDate.of(2026, 4, 12),
                LocalDate.of(2026, 4, 10),
                List.of(30, 5)
        );

        assertThatThrownBy(() -> service.submit(search))
                .isInstanceOf(InvalidSearchRequestException.class)
                .hasMessage("checkIn must be before checkOut");
    }

    @Test
    void shouldThrowSpecificExceptionWhenPublishingFails() {
        final Search search = new Search(
                "hotel-1",
                LocalDate.of(2026, 4, 10),
                LocalDate.of(2026, 4, 12),
                List.of(30, 5)
        );

        when(searchIdGenerator.generate()).thenReturn("search-123");
        doThrow(new RuntimeException("boom")).when(searchEventProducer).send(org.mockito.ArgumentMatchers.any(SearchEvent.class));

        assertThatThrownBy(() -> service.submit(search))
                .isInstanceOf(SearchEventPublishException.class)
                .hasMessage("Error publishing search event")
                .hasCauseInstanceOf(RuntimeException.class);
    }
}