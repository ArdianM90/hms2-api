package com.hms.api.domain.reservation.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MakeReservationRequest(
    int[] roomIds, LocalDate dateStart, LocalDate dateEnd, BigDecimal totalPrice, String comment) {}
