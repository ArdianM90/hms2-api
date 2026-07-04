package com.hms.api.domain.task.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hms.api.common.exception.BusinessException;
import lombok.Getter;

@Getter
public enum TaskStatus {
  ASSIGNED,
  IN_PROGRESS,
  COMPLETED,
  CANCELLED;

  public String getCode() {
    return name().toLowerCase();
  }

  @JsonCreator
  public static TaskStatus fromCode(String code) {
    for (TaskStatus source : values()) {
      if (source.getCode().equalsIgnoreCase(code)) {
        return source;
      }
    }
    throw new BusinessException("Nieznane rodzaj zadania: " + code);
  }
}
