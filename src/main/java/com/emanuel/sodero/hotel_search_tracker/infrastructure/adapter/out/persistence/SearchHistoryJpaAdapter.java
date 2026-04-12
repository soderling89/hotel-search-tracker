package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.out.persistence;

import com.emanuel.sodero.hotel_search_tracker.application.port.out.SearchHistoryRepository;
import com.emanuel.sodero.hotel_search_tracker.domain.model.Search;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SearchHistoryJpaAdapter implements SearchHistoryRepository {

    private final SearchEntityRepository searchEntityRepository;

    public SearchHistoryJpaAdapter(final SearchEntityRepository searchEntityRepository) {
        this.searchEntityRepository = searchEntityRepository;
    }

    @Override
    public void store(final String searchId, final Search search) {
        searchEntityRepository.save(new SearchEntity(
                searchId,
                search.hotelId(),
                search.checkIn(),
                search.checkOut(),
                joinAges(search)
        ));
    }

    private static String joinAges(final Search search) {
        return search.ages().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

}
