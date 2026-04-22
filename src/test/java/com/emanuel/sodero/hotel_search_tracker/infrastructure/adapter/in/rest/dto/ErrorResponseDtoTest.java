package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ErrorResponseDtoTest {

    @Test
    void shouldCopyDetailsWhenProvided() {
        final List<String> details = new ArrayList<>(List.of("field1: must not be blank", "field2: must not be null"));
        final OffsetDateTime timestamp = OffsetDateTime.now();

        final ErrorResponseDto dto = new ErrorResponseDto(
                timestamp,
                400,
                "Bad Request",
                details
        );

        details.add("extra detail");

        assertAll("ErrorResponseDto validation",
                () -> assertThat(dto.timestamp()).isEqualTo(timestamp),
                () -> assertThat(dto.status()).isEqualTo(400),
                () -> assertThat(dto.error()).isEqualTo("Bad Request"),
                () -> assertThat(dto.details())
                        .containsExactly("field1: must not be blank", "field2: must not be null")
        );
    }

    @Test
    void shouldKeepEmptyDetails() {
        final ErrorResponseDto dto = new ErrorResponseDto(
                OffsetDateTime.now(),
                500,
                "Internal Server Error",
                List.of()
        );

        assertThat(dto.details()).isEmpty();
    }
}