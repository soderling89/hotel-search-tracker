package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.out.id;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UuidSearchIdGeneratorTest {

    private final UuidSearchIdGenerator generator = new UuidSearchIdGenerator();

    @Test
    void shouldGenerateNonEmptyUuid() {
        assertThat(generator.generate()).isNotBlank();
    }

    @Test
    void shouldGenerateDifferentIdsOnEachCall() {
        assertThat(generator.generate()).isNotEqualTo(generator.generate());
    }
}
