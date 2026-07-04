package com.hms.api.domain.task.dto;

import com.hms.api.domain.task.model.TaskType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record AddTaskRequest(
    @NotNull UUID assigneeUserId,
    @NotNull UUID createdByUserId,
    Integer roomId,
    Integer reservationId,
    @NotNull TaskType taskTypeCode,
    @NotBlank String title,
    String description,
    Short priority,
    LocalDateTime dueAt) {}
