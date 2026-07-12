package com.hms.api.domain.task.dto;

import com.hms.api.common.dictionary.dto.DictionaryValue;
import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDetails(
    UUID assigneeUserId,
    String assigneeFirstName,
    String assigneeLastName,
    UUID createdByUserId,
    String createdByFirstName,
    String createdByLastName,
    Integer roomId,
    String roomNumber,
    Integer reservationId,
    DictionaryValue taskType,
    DictionaryValue status,
    String title,
    String description,
    Short priority,
    LocalDateTime dueAt,
    LocalDateTime createdAt,
    LocalDateTime startedAt,
    LocalDateTime completedAt) {}
