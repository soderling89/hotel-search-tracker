package com.emanuel.sodero.hotel_search_tracker.application.port.in;

import com.emanuel.sodero.hotel_search_tracker.domain.model.Search;

public interface RegisterSearchUseCase {

    String submit(Search search);
}
