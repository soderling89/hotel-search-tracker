package com.emanuel.sodero.hotel_search_tracker.infrastructure.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableConfigurationProperties(KafkaTopicsProperties.class)
public class ApplicationConfiguration {
    @Bean(destroyMethod = "close")
    ExecutorService virtualThreadExecutorService() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
