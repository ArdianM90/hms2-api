package com.hms.api.common.dictionary.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hms.api.common.exception.BusinessException;
import com.hms.generated.jooq.hms.tables.TypeCitizenship;
import com.hms.generated.jooq.hms.tables.TypeDocumentType;
import com.hms.generated.jooq.hms.tables.TypeRoomStandard;
import lombok.Getter;
import org.jooq.Record;
import org.jooq.Table;

@Getter
public enum DictionaryType {
  ROOM_STANDARDS(TypeRoomStandard.TYPE_ROOM_STANDARD),
  DOCUMENT_TYPES(TypeDocumentType.TYPE_DOCUMENT_TYPE),
  CITIZENSHIP(TypeCitizenship.TYPE_CITIZENSHIP);

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
