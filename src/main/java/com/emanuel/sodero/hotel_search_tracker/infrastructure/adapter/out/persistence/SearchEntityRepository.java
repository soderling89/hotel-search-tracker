package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchEntityRepository extends JpaRepository<SearchEntity, Long> {
}
