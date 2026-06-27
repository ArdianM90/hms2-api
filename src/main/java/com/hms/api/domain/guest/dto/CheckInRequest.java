package com.hms.api.domain.guest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CheckInRequest(
    @NotNull Integer reservationId, @Valid @NotEmpty List<RoomCheckInRequest> rooms) {}
