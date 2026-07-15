package com.hms.api.domain.guest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record GuestCheckInRequest(
    @NotBlank String firstName,
    @NotBlank String lastName,
    String pesel,
    @NotNull LocalDate dateOfBirth,
    @NotNull String documentTypeCode,
    String documentNumber,
    @NotBlank String citizenshipCode,
    String phone) {}
