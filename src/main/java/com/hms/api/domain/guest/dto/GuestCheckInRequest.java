package com.hms.api.domain.guest.dto;

import com.hms.api.domain.guest.model.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record GuestCheckInRequest(
    @NotBlank String firstName,
    @NotBlank String lastName,
    String pesel,
    @NotNull LocalDate dateOfBirth,
    @NotNull DocumentType documentType,
    String documentNumber,
    String citizenshipCode,
    String phone) {}
