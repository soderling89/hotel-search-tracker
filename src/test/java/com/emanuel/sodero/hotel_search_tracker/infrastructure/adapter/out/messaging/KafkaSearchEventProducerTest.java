package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.out.messaging;

import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchEvent;
import com.emanuel.sodero.hotel_search_tracker.infrastructure.config.KafkaTopicsProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaSearchEventProducerTest {

    @Mock
    private KafkaTemplate<String, SearchEvent> kafkaTemplate;

    @Mock
    private KafkaTopicsProperties kafkaTopicsProperties;

    @InjectMocks
    private KafkaSearchEventProducer producer;

    @Test
    void shouldPublishMessageToConfiguredTopic() {
        final SearchEvent searchEvent = new SearchEvent(
                "id-1",
                "hotel-1",
                LocalDate.of(2026, 4, 10),
                LocalDate.of(2026, 4, 11),
                List.of(20)
        );

        when(kafkaTopicsProperties.topic()).thenReturn("topic-1");

        producer.send(searchEvent);

        verify(kafkaTemplate).send("topic-1", "id-1", searchEvent);
    }
}