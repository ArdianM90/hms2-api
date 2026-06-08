package com.hms.api.domain.room.dto;

import java.math.BigDecimal;

public record CreateRoomRequest(
    String roomNumber,
    String roomStandardCode,
    Integer capacity,
    BigDecimal pricePerNight,
    Integer floor,
    Integer areaM2) {}
