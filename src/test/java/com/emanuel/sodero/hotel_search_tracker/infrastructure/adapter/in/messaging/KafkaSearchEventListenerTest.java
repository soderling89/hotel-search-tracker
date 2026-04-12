package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.messaging;

import com.emanuel.sodero.hotel_search_tracker.application.port.in.PersistSearchUseCase;
import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchEvent;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class KafkaSearchEventListenerTest {

    @Test
    void shouldPersistReceivedMessage() throws Exception {
        final PersistSearchUseCase persistSearchUseCase = mock(PersistSearchUseCase.class);
        final KafkaSearchEventListener listener = new KafkaSearchEventListener(
                persistSearchUseCase,
                Executors.newVirtualThreadPerTaskExecutor()
        );
        final SearchEvent searchEvent = new SearchEvent("id-1", "hotel-1", LocalDate.of(2026, 4, 10), LocalDate.of(2026, 4, 11), List.of(1, 2));

        listener.onMessage(searchEvent);

        verify(persistSearchUseCase).store(searchEvent);
    }
}
