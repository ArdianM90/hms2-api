package com.hms.api.domain.user.dto;

import java.util.UUID;

public record EmployeeListItem(
    UUID userId,
    String email,
    String firstName,
    String lastName,
    String roleCode,
    String[] positionCodes) {}
