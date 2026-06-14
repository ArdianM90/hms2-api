package com.hms.api.domain.reservation.dto;

import com.hms.api.domain.reservation.model.ReservationStatus;

public record UpdateReservationStatusRequest(ReservationStatus statusCode) {}
