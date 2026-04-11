package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest;

import com.emanuel.sodero.hotel_search_tracker.domain.model.Search;
import com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto.SearchRequestDto;
import org.springframework.stereotype.Component;

@Component
public class SearchRequestMapper {

    public Search toSearch(final SearchRequestDto searchRequestDto) {
        return new Search(
                searchRequestDto.hotelId(),
                searchRequestDto.checkIn(),
                searchRequestDto.checkOut(),
                searchRequestDto.ages()
        );
    }
}
