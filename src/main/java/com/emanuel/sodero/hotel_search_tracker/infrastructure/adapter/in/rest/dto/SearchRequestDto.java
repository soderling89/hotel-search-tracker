package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.util.List;

public record SearchRequestDto(
        @NotBlank
        @Schema(example = "1234aBc")
        String hotelId,
        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy")
        @Schema(example = "29/12/2023", type = "string", pattern = "dd/MM/yyyy")
        LocalDate checkIn,
        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy")
        @Schema(example = "31/12/2023", type = "string", pattern = "dd/MM/yyyy")
        LocalDate checkOut,
        @NotEmpty
        @Schema(example = "[30,29,1,3]")
        List<@NotNull @PositiveOrZero Integer> ages
) {
    public SearchRequestDto {
        ages = ages == null ? null : List.copyOf(ages);
    }
}
