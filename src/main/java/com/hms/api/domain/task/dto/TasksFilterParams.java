package com.hms.api.domain.task.dto;

import java.time.LocalDate;
import java.util.List;

public record TasksFilterParams(
    String query, List<String> taskTypeCodes, LocalDate dueFrom, LocalDate dueTo) {}
