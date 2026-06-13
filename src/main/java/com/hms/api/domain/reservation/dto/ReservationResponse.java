package com.hms.api.domain.reservation.dto;

import com.hms.api.common.jackson.CodeLabelResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationResponse(
    Integer reservationId,
    LocalDate startDate,
    LocalDate endDate,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    CodeLabelResponse reservationStatus,
    CodeLabelResponse reservationSource,
    BigDecimal totalPrice,
    Long roomsQty) {}
