package com.hms.api.domain.guest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RoomCheckInRequest(
    @NotNull Integer roomId, @Valid @NotEmpty List<GuestCheckInRequest> guests) {}
