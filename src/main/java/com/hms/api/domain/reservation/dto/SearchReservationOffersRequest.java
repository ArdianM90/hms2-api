package com.hms.api.domain.reservation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record SearchReservationOffersRequest(
    @NotNull LocalDate startDate,
    @NotNull LocalDate endDate,
    @NotEmpty Integer[] roomCapacities,
    String standardCode,
    Integer priceFrom,
    Integer priceTo) {}
