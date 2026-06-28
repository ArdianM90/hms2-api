package com.hms.api.domain.user.dto;

public record EmployeeRequest(
    String email, String firstName, String lastName, String roleCode, String[] positionCodes) {}
