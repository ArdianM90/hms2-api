package com.hms.api.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hms.api.domain.reservation.model.ReservationSource;
import com.hms.api.domain.reservation.model.ReservationStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationDto(
    Integer reservationId,
    LocalDate startDate,
    LocalDate endDate,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    @JsonFormat(shape = JsonFormat.Shape.OBJECT) ReservationStatus reservationStatus,
    @JsonFormat(shape = JsonFormat.Shape.OBJECT) ReservationSource reservationSource,
    BigDecimal totalPrice,
    Long roomsQty) {}
