package com.emanuel.sodero.hotel_search_tracker.infrastructure.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationTest {

    @Test
    void shouldCreateVirtualThreadExecutor() {
        final var executor = new ApplicationConfiguration().virtualThreadExecutorService();

        assertThat(executor).isNotNull();
        executor.close();
    }
}
