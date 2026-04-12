package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest;

import com.emanuel.sodero.hotel_search_tracker.application.port.in.GetSearchCountUseCase;
import com.emanuel.sodero.hotel_search_tracker.application.port.in.RegisterSearchUseCase;
import com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto.SearchCountResponseDto;
import com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto.SearchIdResponseDto;
import com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto.SearchRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping
@Tag(name = "Searches", description = "Endpoints para registrar y consultar busquedas")
public class SearchController {

    private final RegisterSearchUseCase registerSearchUseCase;
    private final GetSearchCountUseCase getSearchCountUseCase;
    private final SearchRequestMapper searchRequestMapper;

    public SearchController(
            final RegisterSearchUseCase registerSearchUseCase,
            final GetSearchCountUseCase getSearchCountUseCase,
            final SearchRequestMapper searchRequestMapper
    ) {
        this.registerSearchUseCase = registerSearchUseCase;
        this.getSearchCountUseCase = getSearchCountUseCase;
        this.searchRequestMapper = searchRequestMapper;
    }

    @PostMapping("/search")
    @Operation(summary = "Registra una busqueda y publica el evento en Kafka")
    @ApiResponse(responseCode = "200", description = "Busqueda registrada")
    public SearchIdResponseDto submitSearch(@Valid @RequestBody final SearchRequestDto request) {
        return new SearchIdResponseDto(registerSearchUseCase.submit(searchRequestMapper.toSearch(request)));
    }

    @GetMapping("/count")
    @Operation(summary = "Obtiene la cantidad de busquedas iguales por searchId")
    @ApiResponse(responseCode = "200", description = "Conteo obtenido")
    @ApiResponse(responseCode = "404", description = "searchId no encontrado")
    public SearchCountResponseDto getSearchCount(@RequestParam @NotBlank final String searchId) {
        return searchRequestMapper.toResponse(getSearchCountUseCase.getBySearchId(searchId));
    }
}
