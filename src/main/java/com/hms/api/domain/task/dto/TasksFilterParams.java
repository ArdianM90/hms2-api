package com.hms.api.domain.task.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TasksFilterParams(
    UUID appUserId,
    String query,
    List<String> taskTypeCodes,
    List<String> taskStatusCodes,
    LocalDate dueFrom,
    LocalDate dueTo) {}
