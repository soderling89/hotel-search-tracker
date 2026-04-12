package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.out.messaging;

import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchEvent;
import com.emanuel.sodero.hotel_search_tracker.infrastructure.config.KafkaTopicsProperties;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class KafkaSearchEventProducerTest {

    @Test
    void shouldPublishMessageToConfiguredTopic() {
        final KafkaTemplate<String, SearchEvent> kafkaTemplate = mock(KafkaTemplate.class);
        final KafkaSearchEventProducer producer = new KafkaSearchEventProducer(kafkaTemplate, new KafkaTopicsProperties("topic-1"));
        final SearchEvent searchEvent = new SearchEvent("id-1", "hotel-1", LocalDate.of(2026, 4, 10), LocalDate.of(2026, 4, 11), List.of(20));

        producer.send(searchEvent);

        final ArgumentCaptor<String> topic = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> key = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<SearchEvent> payload = ArgumentCaptor.forClass(SearchEvent.class);
        verify(kafkaTemplate).send(topic.capture(), key.capture(), payload.capture());
        assertThat(topic.getValue()).isEqualTo("topic-1");
        assertThat(key.getValue()).isEqualTo("id-1");
        assertThat(payload.getValue()).isEqualTo(searchEvent);
    }
}
