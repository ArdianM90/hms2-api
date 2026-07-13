package com.hms.api.domain.reservation.dto;

import com.hms.api.common.dictionary.dto.DictionaryValue;
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
    DictionaryValue reservationStatus,
    DictionaryValue reservationSource,
    List<RoomDto> rooms,
    String comment) {}
