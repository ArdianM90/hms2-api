package com.hms.api.domain.reservation.dto;

import java.time.LocalDate;

public record SearchReservationOffersRequest(
    LocalDate startDate,
    LocalDate endDate,
    Integer[] roomCapacities,
    String standardCode,
    Integer priceFrom,
    Integer priceTo) {}
