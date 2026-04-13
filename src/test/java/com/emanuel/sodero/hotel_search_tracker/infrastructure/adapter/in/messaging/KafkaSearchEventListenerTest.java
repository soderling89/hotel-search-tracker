package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.messaging;

import com.emanuel.sodero.hotel_search_tracker.application.port.in.PersistSearchUseCase;
import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaSearchEventListenerTest {

    @Mock
    private PersistSearchUseCase persistSearchUseCase;

    @Test
    void shouldPersistReceivedMessage() throws Exception {
        final SearchEvent searchEvent = new SearchEvent(
                "id-1",
                "hotel-1",
                LocalDate.of(2026, 4, 10),
                LocalDate.of(2026, 4, 11),
                List.of(1, 2)
        );

        final var customListener = new KafkaSearchEventListener(
                persistSearchUseCase,
                Executors.newVirtualThreadPerTaskExecutor()
        );

        customListener.onMessage(searchEvent);

        verify(persistSearchUseCase).store(searchEvent);
    }
}