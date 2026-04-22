package com.emanuel.sodero.hotel_search_tracker.infrastructure.config;

import com.emanuel.sodero.hotel_search_tracker.application.port.in.GetSearchCountUseCase;
import com.emanuel.sodero.hotel_search_tracker.application.port.in.PersistSearchUseCase;
import com.emanuel.sodero.hotel_search_tracker.application.port.in.RegisterSearchUseCase;
import com.emanuel.sodero.hotel_search_tracker.application.port.out.SearchEventProducer;
import com.emanuel.sodero.hotel_search_tracker.application.port.out.SearchHistoryRepository;
import com.emanuel.sodero.hotel_search_tracker.application.port.out.SearchIdGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
class ConfigurationTest {

    @Mock
    private SearchIdGenerator searchIdGenerator;

    @Mock
    private SearchEventProducer searchEventProducer;

    @Mock
    private SearchHistoryRepository searchHistoryRepository;

    @Test
    void shouldCreateVirtualThreadExecutor() {
        final var executor = new ApplicationConfiguration().virtualThreadExecutorService();

        assertThat(executor).isNotNull();
        executor.close();
    }

    @Test
    void shouldCreateUseCaseBeans() {
        final ApplicationConfiguration configuration = new ApplicationConfiguration();

        final RegisterSearchUseCase registerSearchUseCase = configuration.registerSearchUseCase(searchIdGenerator, searchEventProducer);
        final GetSearchCountUseCase getSearchCountUseCase = configuration.getSearchCountUseCase(searchHistoryRepository);
        final PersistSearchUseCase persistSearchUseCase = configuration.persistSearchUseCase(searchHistoryRepository);

        assertAll(
                () -> assertThat(registerSearchUseCase).isNotNull(),
                () -> assertThat(getSearchCountUseCase).isNotNull(),
                () -> assertThat(persistSearchUseCase).isNotNull()
        );
    }
}
