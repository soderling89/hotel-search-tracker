package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.out.persistence;

import com.emanuel.sodero.hotel_search_tracker.domain.model.Search;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@ActiveProfiles("test")
@Import(SearchHistoryJpaAdapter.class)
class SearchHistoryJpaAdapterTest {

    @Autowired
    private SearchHistoryJpaAdapter adapter;

    @Autowired
    private SearchEntityRepository searchEntityRepository;

    @Test
    void shouldPersistAndRecoverSearchPreservingAgeOrder() {
        final Search search = new Search("hotel-1", LocalDate.of(2026, 4, 10), LocalDate.of(2026, 4, 12), List.of(3, 29, 30, 1));

        adapter.store("id-1", search);
        adapter.store("id-2", search);

        assertAll("adapter persistence and recovery",
                () -> assertThat(adapter.countMatches(search)).isEqualTo(2L),
                () -> assertThat(adapter.findBySearchId("id-1")).contains(search),
                () -> assertThat(adapter.findBySearchId("missing")).isEmpty()
        );
    }

    @Test
    void shouldReturnEmptyAgeListWhenStoredValueIsBlank() {
        searchEntityRepository.save(new SearchEntity(
                "id-blank",
                "hotel-1",
                LocalDate.of(2026, 4, 10),
                LocalDate.of(2026, 4, 12),
                ""
        ));

        assertThat(adapter.findBySearchId("id-blank"))
                .get()
                .satisfies(search -> assertThat(search.ages()).isEmpty());
    }
}
