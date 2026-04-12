package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record SearchCountResponseDto(
        @Schema(example = "b6aeb1a3-e498-3eb9-bc2f-1f671f56f5c4")
        String searchId,
        SearchRequestDto search,
        @Schema(example = "100")
        long count
) {
}
