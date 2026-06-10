package com.hms.api.domain.reservation.dto;

import com.hms.api.domain.room.dto.RoomStandard;
import java.math.BigDecimal;

public record RoomOffer(RoomStandard standard, Integer capacity, BigDecimal pricePerNight) {}
