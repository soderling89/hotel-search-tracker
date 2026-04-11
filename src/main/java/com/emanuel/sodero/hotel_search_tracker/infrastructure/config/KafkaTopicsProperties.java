package com.emanuel.sodero.hotel_search_tracker.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hotel-search.kafka")
public record KafkaTopicsProperties(String topic) {
}
