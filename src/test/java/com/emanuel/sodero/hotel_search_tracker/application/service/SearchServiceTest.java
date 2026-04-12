package com.emanuel.sodero.hotel_search_tracker.application.service;

import com.emanuel.sodero.hotel_search_tracker.application.port.out.SearchEventProducer;
import com.emanuel.sodero.hotel_search_tracker.application.port.out.SearchIdGenerator;
import com.emanuel.sodero.hotel_search_tracker.domain.model.Search;
import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchEvent;
import com.emanuel.sodero.hotel_search_tracker.domain.exception.InvalidSearchRequestException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class SearchServiceTest {

    @Test
    void shouldGenerateIdAndPublishMessage() {
        final SearchIdGenerator searchIdGenerator = mock(SearchIdGenerator.class);
        final SearchEventProducer searchEventProducer = mock(SearchEventProducer.class);
        final SearchService service = new SearchService(searchIdGenerator, searchEventProducer);
        final Search search = new Search("hotel-1", LocalDate.of(2026, 4, 10), LocalDate.of(2026, 4, 12), List.of(30, 5));

        when(searchIdGenerator.generate()).thenReturn("search-123");

        final String response = service.submit(search);

        final ArgumentCaptor<SearchEvent> captor = ArgumentCaptor.forClass(SearchEvent.class);
        verify(searchEventProducer).send(captor.capture());
        assertThat(response).isEqualTo("search-123");
        assertThat(captor.getValue().searchId()).isEqualTo("search-123");
        assertThat(captor.getValue().hotelId()).isEqualTo("hotel-1");
        assertThat(captor.getValue().ages()).containsExactly(30, 5);
    }

    @Test
    void shouldRejectInvalidStayDates() {
        final SearchIdGenerator searchIdGenerator = mock(SearchIdGenerator.class);
        final SearchEventProducer searchEventProducer = mock(SearchEventProducer.class);
        final SearchService service = new SearchService(searchIdGenerator, searchEventProducer);
        final Search search = new Search("hotel-1", LocalDate.of(2026, 4, 12), LocalDate.of(2026, 4, 10), List.of(30, 5));

        assertThatThrownBy(() -> service.submit(search))
                .isInstanceOf(InvalidSearchRequestException.class)
                .hasMessage("checkIn must be before checkOut");
    }
}
