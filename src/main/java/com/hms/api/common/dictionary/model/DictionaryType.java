package com.hms.api.common.dictionary.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hms.api.common.exception.BusinessException;
import com.hms.generated.jooq.auth.tables.TypeAppUserRole;
import com.hms.generated.jooq.hms.tables.*;
import lombok.Getter;
import org.jooq.Record;
import org.jooq.Table;

@Getter
public enum DictionaryType {
  RESERVATION_STATUS(TypeReservationStatus.TYPE_RESERVATION_STATUS),
  RESERVATION_SOURCE(TypeReservationSource.TYPE_RESERVATION_SOURCE),
  ROOM_STANDARD(TypeRoomStandard.TYPE_ROOM_STANDARD),
  DOCUMENT_TYPE(TypeDocumentType.TYPE_DOCUMENT_TYPE),
  CITIZENSHIP(TypeCitizenship.TYPE_CITIZENSHIP),
  APP_USER_ROLE(TypeAppUserRole.TYPE_APP_USER_ROLE),
  EMPLOYEE_POSITION(TypeEmployeePosition.TYPE_EMPLOYEE_POSITION),
  EMPLOYEE_TASK(TypeEmployeeTask.TYPE_EMPLOYEE_TASK),
  TASK_STATUS(TypeEmployeeTaskStatus.TYPE_EMPLOYEE_TASK_STATUS);

  private final Table<? extends Record> table;

  DictionaryType(Table<? extends Record> table) {
    this.table = table;
  }

  @JsonValue
  public String getCode() {
    return name().toLowerCase();
  }

  @JsonCreator
  public static DictionaryType fromCode(String code) {
    for (DictionaryType type : values()) {
      if (type.name().equalsIgnoreCase(code)) {
        return type;
      }
    }
    throw new BusinessException("Nieznany typ słownika: " + code);
  }
}
