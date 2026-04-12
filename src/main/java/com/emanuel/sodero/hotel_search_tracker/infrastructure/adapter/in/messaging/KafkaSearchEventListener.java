package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.in.messaging;

import com.emanuel.sodero.hotel_search_tracker.application.port.in.PersistSearchUseCase;
import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

@Component
public class KafkaSearchEventListener {

    private final PersistSearchUseCase persistSearchUseCase;
    private final ExecutorService virtualThreadExecutorService;

    public KafkaSearchEventListener(
            final PersistSearchUseCase persistSearchUseCase,
            final ExecutorService virtualThreadExecutorService
    ) {
        this.persistSearchUseCase = persistSearchUseCase;
        this.virtualThreadExecutorService = virtualThreadExecutorService;
    }

    @KafkaListener(topics = "${hotel-search.kafka.topic}")
    public void onMessage(final SearchEvent searchEvent) throws ExecutionException, InterruptedException {
        virtualThreadExecutorService.submit(() -> persistSearchUseCase.store(searchEvent)).get();
    }
}
