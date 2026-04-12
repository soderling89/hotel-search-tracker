package com.emanuel.sodero.hotel_search_tracker.application.port.in;

import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchCountView;

public interface GetSearchCountUseCase {

    SearchCountView getBySearchId(String searchId);
}
