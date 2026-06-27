package com.hms.api.domain.guest.dto;

import com.hms.api.domain.guest.model.DocumentType;
import java.time.LocalDate;

public record ReservationRoomDto(
    int reservationId,
    int roomId,
    String firstName,
    String lastName,
    String pesel,
    LocalDate dateOfBirth,
    DocumentType documentType,
    String documentNumber,
    String citizenshipCode,
    String phone) {}
