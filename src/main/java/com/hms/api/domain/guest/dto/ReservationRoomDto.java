package com.hms.api.domain.guest.dto;

import java.time.LocalDate;

public record ReservationRoomDto(
    int reservationId,
    int roomId,
    String firstName,
    String lastName,
    String pesel,
    LocalDate dateOfBirth,
    String documentTypeCode,
    String documentNumber,
    String citizenshipCode,
    String phone) {}
