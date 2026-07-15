package com.hms.api.domain.user.dto;

import java.util.List;

public record EmployeesFilterParams(String query, String roleCode, List<String> positionCodes) {}
