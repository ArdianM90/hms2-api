package com.hms.api.domain.task.dto;

import com.hms.api.domain.task.model.TaskStatus;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record UpdateStatusRequest(@NotNull TaskStatus statusCode, LocalDateTime completedAt) {}
