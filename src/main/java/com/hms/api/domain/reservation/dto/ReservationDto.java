package com.hms.api.domain.reservation.dto;

import com.hms.api.domain.room.dto.RoomStandard;
import java.time.LocalDate;

public record ReservationDto(
    Integer reservationId,
    LocalDate startDate,
    LocalDate endDate,
    RoomStandard roomStandard,
    ReservationStatus reservationStatus,
    Integer roomId) {}
