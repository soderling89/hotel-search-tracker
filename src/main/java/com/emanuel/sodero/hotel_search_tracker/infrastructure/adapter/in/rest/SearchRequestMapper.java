package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest;

import com.emanuel.sodero.hotel_search_tracker.domain.model.Search;
import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchCountView;
import com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto.SearchCountResponseDto;
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

    public SearchCountResponseDto toResponse(final SearchCountView searchCountView) {
        return new SearchCountResponseDto(
                searchCountView.searchId(),
                toRequestDto(searchCountView.search()),
                searchCountView.count()
        );
    }

    private SearchRequestDto toRequestDto(final Search search) {
        return new SearchRequestDto(
                search.hotelId(),
                search.checkIn(),
                search.checkOut(),
                search.ages()
        );
    }
}
