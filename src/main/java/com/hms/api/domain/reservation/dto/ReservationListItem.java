package com.hms.api.domain.reservation.dto;

import com.hms.api.common.dictionary.dto.DictionaryValue;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationListItem(
    Integer reservationId,
    String guestFirstName,
    String guestLastName,
    LocalDate startDate,
    LocalDate endDate,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Long daysQty,
    DictionaryValue reservationStatus,
    DictionaryValue reservationSource,
    BigDecimal totalPrice,
    Long roomsQty) {}
