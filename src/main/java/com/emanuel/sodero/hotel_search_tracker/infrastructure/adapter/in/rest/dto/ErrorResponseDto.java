package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.List;

@Schema(description = "Estructura de error para respuestas HTTP")
public record ErrorResponseDto(
        @Schema(description = "Fecha y hora del error")
        OffsetDateTime timestamp,
        @Schema(description = "Codigo HTTP", example = "400")
        int status,
        @Schema(description = "Breve descripcion del error")
        String error,
        @Schema(description = "Detalle o lista de errores detectados")
        List<String> details
) {
    public ErrorResponseDto {
        details = List.copyOf(details);
    }
}
