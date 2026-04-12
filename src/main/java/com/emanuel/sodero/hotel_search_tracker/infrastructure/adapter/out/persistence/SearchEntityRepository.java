package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SearchEntityRepository extends JpaRepository<SearchEntity, Long> {

    Optional<SearchEntity> findFirstBySearchId(String searchId);

    long countByHotelIdAndCheckInDateAndCheckOutDateAndAges(
            String hotelId,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            String ages
    );
}
