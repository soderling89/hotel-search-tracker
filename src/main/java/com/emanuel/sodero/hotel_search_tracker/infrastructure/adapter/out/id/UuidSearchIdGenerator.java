package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.out.id;

import com.emanuel.sodero.hotel_search_tracker.domain.port.out.SearchIdGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidSearchIdGenerator implements SearchIdGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
