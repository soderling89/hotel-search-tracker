package com.emanuel.sodero.hotel_search_tracker.infrastructure.config;

import com.emanuel.sodero.hotel_search_tracker.application.port.in.GetSearchCountUseCase;
import com.emanuel.sodero.hotel_search_tracker.application.port.in.PersistSearchUseCase;
import com.emanuel.sodero.hotel_search_tracker.application.port.in.RegisterSearchUseCase;
import com.emanuel.sodero.hotel_search_tracker.domain.port.out.SearchEventProducer;
import com.emanuel.sodero.hotel_search_tracker.domain.port.out.SearchHistoryRepository;
import com.emanuel.sodero.hotel_search_tracker.domain.port.out.SearchIdGenerator;
import com.emanuel.sodero.hotel_search_tracker.application.service.SearchService;
import com.emanuel.sodero.hotel_search_tracker.application.service.SearchStatsService;
import com.emanuel.sodero.hotel_search_tracker.application.service.SearchStorageService;
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

    @Bean
    RegisterSearchUseCase registerSearchUseCase(final SearchIdGenerator searchIdGenerator, final SearchEventProducer searchEventProducer) {
        return new SearchService(searchIdGenerator, searchEventProducer);
    }

    @Bean
    GetSearchCountUseCase getSearchCountUseCase(final SearchHistoryRepository searchHistoryRepository) {
        return new SearchStatsService(searchHistoryRepository);
    }

    @Bean
    PersistSearchUseCase persistSearchUseCase(final SearchHistoryRepository searchHistoryRepository) {
        return new SearchStorageService(searchHistoryRepository);
    }
}
