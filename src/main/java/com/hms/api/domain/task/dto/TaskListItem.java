package com.hms.api.domain.task.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskListItem(
    Integer employeeTaskId,
    UUID assigneeUserId,
    String assigneeFirstName,
    String assigneeLastName,
    UUID createdByUserId,
    String createdByFirstName,
    String createdByLastName,
    Integer roomId,
    String roomNumber,
    Integer reservationId,
    String taskTypeCode,
    String taskType,
    String statusCode,
    String status,
    String title,
    String description,
    Short priority,
    LocalDateTime dueAt,
    LocalDateTime createdAt,
    LocalDateTime startedAt,
    LocalDateTime completedAt) {}
