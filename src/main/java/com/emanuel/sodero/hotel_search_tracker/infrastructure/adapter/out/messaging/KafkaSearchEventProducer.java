package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.out.messaging;

import com.emanuel.sodero.hotel_search_tracker.domain.port.out.SearchEventProducer;
import com.emanuel.sodero.hotel_search_tracker.domain.model.SearchEvent;
import com.emanuel.sodero.hotel_search_tracker.infrastructure.config.KafkaTopicsProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSearchEventProducer implements SearchEventProducer {

    private final KafkaTemplate<String, SearchEvent> kafkaTemplate;
    private final KafkaTopicsProperties kafkaTopicsProperties;

    public KafkaSearchEventProducer(
            final KafkaTemplate<String, SearchEvent> kafkaTemplate,
            final KafkaTopicsProperties kafkaTopicsProperties
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopicsProperties = kafkaTopicsProperties;
    }

    @Override
    public void send(final SearchEvent searchEvent) {
        kafkaTemplate.send(kafkaTopicsProperties.topic(), searchEvent.searchId(), searchEvent);
    }
}
