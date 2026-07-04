package com.hms.api.domain.task.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hms.api.common.exception.BusinessException;
import lombok.Getter;

@Getter
public enum TaskType {
  ROOM_SERVICE,
  PREPARE_ROOM,
  TECHNICAL_ISSUE,
  MAINTENANCE,
  SECURITY_CHECK,
  RECEPTION,
  OTHER;

  public String getCode() {
    return name().toLowerCase();
  }

  @JsonCreator
  public static TaskType fromCode(String code) {
    for (TaskType source : values()) {
      if (source.getCode().equalsIgnoreCase(code)) {
        return source;
      }
    }
    throw new BusinessException("Nieznane rodzaj zadania: " + code);
  }
}
