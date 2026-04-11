package com.emanuel.sodero.hotel_search_tracker.application.service;

import com.emanuel.sodero.hotel_search_tracker.application.port.out.SearchEventProducer;
import com.emanuel.sodero.hotel_search_tracker.application.port.out.SearchIdGenerator;
import com.emanuel.sodero.hotel_search_tracker.domain.model.Search;
import com.emanuel.sodero.hotel_search_tracker.domain.model.exception.InvalidSearchRequestException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class SearchServiceTest {

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
