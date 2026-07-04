package com.hms.api.domain.task.dto;

import com.hms.api.domain.task.model.TaskStatus;
import com.hms.api.domain.task.model.TaskType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateTaskRequest(
    @NotNull UUID assigneeUserId,
    @NotNull UUID createdByUserId,
    Integer roomId,
    Integer reservationId,
    @NotNull TaskType taskTypeCode,
    @NotNull TaskStatus statusCode,
    @NotBlank String title,
    String description,
    Short priority,
    LocalDateTime dueAt) {}
