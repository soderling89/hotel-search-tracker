package com.emanuel.sodero.hotel_search_tracker.application.service;

import com.emanuel.sodero.hotel_search_tracker.application.port.in.RegisterSearchUseCase;
import com.emanuel.sodero.hotel_search_tracker.application.port.out.SearchEventProducer;
import com.emanuel.sodero.hotel_search_tracker.application.port.out.SearchIdGenerator;
import com.emanuel.sodero.hotel_search_tracker.domain.model.Search;
import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchEvent;
import com.emanuel.sodero.hotel_search_tracker.domain.exception.InvalidSearchRequestException;
import org.springframework.stereotype.Service;

@Service
public class SearchService implements RegisterSearchUseCase {
    private final SearchIdGenerator searchIdGenerator;
    private final SearchEventProducer searchEventProducer;

    public SearchService(
            final SearchIdGenerator searchIdGenerator,
            final SearchEventProducer searchEventProducer
    ) {
        this.searchIdGenerator = searchIdGenerator;
        this.searchEventProducer = searchEventProducer;
    }

    public String submit(final Search search) {
        validateDates(search);
        final String searchId = searchIdGenerator.generate();
        try {
            searchEventProducer.send(new SearchEvent(
                    searchId,
                    search.hotelId(),
                    search.checkIn(),
                    search.checkOut(),
                    search.ages()
            ));
        } catch (Exception e) {
            throw new RuntimeException("Error publishing search event", e);
        }
        return searchId;
    }

    private void validateDates(final Search search) {
        if (!search.checkIn().isBefore(search.checkOut())) {
            throw new InvalidSearchRequestException("checkIn must be before checkOut");
        }
    }

}

