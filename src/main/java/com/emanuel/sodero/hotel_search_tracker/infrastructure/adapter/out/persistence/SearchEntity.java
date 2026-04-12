package com.emanuel.sodero.hotel_search_tracker.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "hotel_searches")
public class SearchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "search_id", nullable = false, length = 36)
    private String searchId;

    @Column(name = "hotel_id", nullable = false, length = 50)
    private String hotelId;

    @Column(name = "check_in", nullable = false)
    private LocalDate checkInDate;

    @Column(name = "check_out", nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "ages", nullable = false, length = 100)
    private String ages;

    protected SearchEntity() {
    }

    public SearchEntity(
            final String searchId,
            final String hotelId,
            final LocalDate checkIn,
            final LocalDate checkOut,
            final String ages
    ) {
        this.searchId = searchId;
        this.hotelId = hotelId;
        this.checkInDate = checkIn;
        this.checkOutDate = checkOut;
        this.ages = ages;
    }

    public Long getId() {
        return id;
    }

    public String getSearchId() {
        return searchId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public String getAges() {
        return ages;
    }
}
