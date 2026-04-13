package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest;

import com.emanuel.sodero.hotel_search_tracker.domain.exception.InvalidSearchRequestException;
import com.emanuel.sodero.hotel_search_tracker.domain.exception.SearchNotFoundException;
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

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(400);
        assertThat(response.getBody().error()).isEqualTo("Bad Request");
        assertThat(response.getBody().details()).containsExactly("checkIn must be before checkOut");
    }

    @Test
    void shouldHandleSearchNotFoundAsNotFound() {
        final var response = handler.handleNotFound(new SearchNotFoundException("abc"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(404);
        assertThat(response.getBody().details()).containsExactly("Search not found for id abc");
    }

    @Test
    void shouldHandleUnexpectedExceptionAsInternalServerError() {
        final var response = handler.handleUnexpected();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(500);
        assertThat(response.getBody().details()).containsExactly("Unexpected internal error");
    }
}