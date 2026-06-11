package com.hms.api.domain.reservation.dto;

import java.math.BigDecimal;
import java.util.List;

public record ReservationOffer(int numberOfNights, BigDecimal totalPrice, List<RoomOffer> rooms) {}
