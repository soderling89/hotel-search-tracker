package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.out.persistence;

import com.emanuel.sodero.hotel_search_tracker.domain.port.out.SearchHistoryRepository;
import com.emanuel.sodero.hotel_search_tracker.domain.model.Search;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SearchHistoryJpaAdapter implements SearchHistoryRepository {

    private final SearchEntityRepository searchEntityRepository;

    public SearchHistoryJpaAdapter(final SearchEntityRepository searchEntityRepository) {
        this.searchEntityRepository = searchEntityRepository;
    }

    @Override
    @Transactional
    public void store(final String searchId, final Search search) {
        searchEntityRepository.save(new SearchEntity(
                searchId,
                search.hotelId(),
                search.checkIn(),
                search.checkOut(),
                joinAges(search)
        ));
    }

    @Override
    public Optional<Search> findBySearchId(final String searchId) {
        return searchEntityRepository.findFirstBySearchId(searchId)
                .map(entity -> new Search(
                        entity.getHotelId(),
                        entity.getCheckInDate(),
                        entity.getCheckOutDate(),
                        parseAges(entity.getAges())
                ));
    }

    @Override
    public long countMatches(final Search search) {
        return searchEntityRepository.countByHotelIdAndCheckInDateAndCheckOutDateAndAges(
                search.hotelId(),
                search.checkIn(),
                search.checkOut(),
                joinAges(search)
        );
    }

    private static String joinAges(final Search search) {
        return search.ages().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    private static java.util.List<Integer> parseAges(final String ages) {
        if (ages.isBlank()) {
            return java.util.List.of();
        }
        return Arrays.stream(ages.split(","))
                .map(Integer::parseInt)
                .toList();
    }

}
