package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record ErrorResponseDto(
        OffsetDateTime timestamp,
        int status,
        String error,
        List<String> details
) {
    public ErrorResponseDto {
        details = List.copyOf(details);
    }
}
