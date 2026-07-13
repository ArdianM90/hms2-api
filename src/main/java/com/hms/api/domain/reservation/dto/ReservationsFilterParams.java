package com.hms.api.domain.reservation.dto;

import java.time.LocalDate;

public record ReservationsFilterParams(
    String query, String reservationStatusCode, LocalDate createdFrom, LocalDate createdTo) {}
