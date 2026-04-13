package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest;

import com.emanuel.sodero.hotel_search_tracker.domain.exception.InvalidSearchRequestException;
import com.emanuel.sodero.hotel_search_tracker.domain.exception.SearchEventPublishException;
import com.emanuel.sodero.hotel_search_tracker.domain.exception.SearchNotFoundException;
import com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest.dto.ErrorResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class RestExceptionHandlerTest {

    private final RestExceptionHandler handler = new RestExceptionHandler();

    @Test
    void shouldHandleInvalidSearchRequestAsBadRequest() {
        final var response = handler.handleBadRequest(
                new InvalidSearchRequestException("checkIn must be before checkOut")
        );

        assertErrorResponse(response, HttpStatus.BAD_REQUEST, "checkIn must be before checkOut");
    }

    @Test
    void shouldHandleSearchNotFoundAsNotFound() {
        final var response = handler.handleNotFound(new SearchNotFoundException("abc"));

        assertErrorResponse(response, HttpStatus.NOT_FOUND, "Search not found for id abc");
    }

    @Test
    void shouldHandleSearchEventPublishExceptionAsInternalServerError() {
        final var response = handler.handleSearchEventPublish(
                new SearchEventPublishException("Error publishing search event", new RuntimeException("boom"))
        );

        assertErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Error publishing search event");
    }

    @Test
    void shouldHandleUnexpectedExceptionAsInternalServerError() {
        final var response = handler.handleUnexpected();

        assertErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected internal error");
    }

    private void assertErrorResponse(
            final org.springframework.http.ResponseEntity<ErrorResponseDto> response,
            final HttpStatus expectedStatus,
            final String expectedDetail
    ) {
        assertThat(response.getStatusCode()).isEqualTo(expectedStatus);
        assertThat(response.getBody()).isNotNull();

        final ErrorResponseDto body = response.getBody();
        assertThat(body.status()).isEqualTo(expectedStatus.value());
        assertThat(body.error()).isEqualTo(expectedStatus.getReasonPhrase());
        assertThat(body.details()).containsExactly(expectedDetail);
    }
}