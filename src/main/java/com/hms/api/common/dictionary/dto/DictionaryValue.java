package com.hms.api.common.dictionary.dto;

import org.jooq.Field;
import org.jooq.Record;

public record DictionaryValue(String code, String name) {

  public static DictionaryValue of(Record r, Field<String> codeField, Field<String> nameField) {
    return new DictionaryValue(r.get(codeField), r.get(nameField));
  }
}
