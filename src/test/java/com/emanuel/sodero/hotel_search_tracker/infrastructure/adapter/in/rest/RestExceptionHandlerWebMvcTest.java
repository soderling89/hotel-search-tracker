package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestExceptionHandlerWebMvcTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldHandleMethodArgumentNotValidExceptionAsBadRequest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(new TestController())
                .setControllerAdvice(new RestExceptionHandler())
                .build();

        final var request = new TestRequest("");

        mockMvc.perform(post("/test/body")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.details[0]").value("name: must not be blank"));
    }

    @RestController
    static class TestController {

        @SuppressWarnings("unused")
        @PostMapping("/test/body")
        void validateBody(@Valid @RequestBody TestRequest request) {
            // Used only to trigger validation for tests
        }
    }

    record TestRequest(@NotBlank String name) {
    }
}