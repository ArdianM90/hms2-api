package com.hms.api.domain.room.dto;

import java.math.BigDecimal;

public record RoomDto(
    Integer roomId,
    String roomNumber,
    RoomStandard standard,
    Integer capacity,
    BigDecimal pricePerNight,
    Integer floor,
    Integer areaM2) {}
