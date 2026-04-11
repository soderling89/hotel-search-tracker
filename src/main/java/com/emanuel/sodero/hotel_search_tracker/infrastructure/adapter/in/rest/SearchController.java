package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest;

import com.emanuel.sodero.hotel_search_tracker.application.port.in.RegisterSearchUseCase;
import com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto.SearchIdResponseDto;
import com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto.SearchRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping
@Tag(name = "Searches", description = "Endpoints para registrar busquedas")
public class SearchController {

    private final RegisterSearchUseCase registerSearchUseCase;
    private final SearchRequestMapper searchRequestMapper;

    public SearchController(
            final RegisterSearchUseCase registerSearchUseCase,
            final SearchRequestMapper searchRequestMapper
    ) {
        this.registerSearchUseCase = registerSearchUseCase;
        this.searchRequestMapper = searchRequestMapper;
    }

    @PostMapping("/search")
    @Operation(summary = "Registra una busqueda y publica el evento en Kafka")
    @ApiResponse(responseCode = "200", description = "Busqueda registrada")
    public SearchIdResponseDto submitSearch(@Valid @RequestBody final SearchRequestDto request) {
        return new SearchIdResponseDto(registerSearchUseCase.submit(searchRequestMapper.toSearch(request)));
    }
}
