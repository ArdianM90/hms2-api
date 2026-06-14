package com.hms.api.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hms.api.domain.reservation.model.ReservationSource;
import com.hms.api.domain.reservation.model.ReservationStatus;
import com.hms.api.domain.room.dto.RoomDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ReservationDetails(
    Integer reservationId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDate startDate,
    LocalDate endDate,
    BigDecimal totalPrice,
    @JsonFormat(shape = JsonFormat.Shape.OBJECT) ReservationStatus reservationStatus,
    @JsonFormat(shape = JsonFormat.Shape.OBJECT) ReservationSource reservationSource,
    List<RoomDto> rooms,
    String comment) {}
