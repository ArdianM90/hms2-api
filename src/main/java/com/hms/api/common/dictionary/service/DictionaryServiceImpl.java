package com.hms.api.common.dictionary.service;

import com.hms.api.common.dictionary.dto.DictionaryValue;
import com.hms.api.common.dictionary.model.DictionaryType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {

  private final DSLContext dsl;

  public List<DictionaryValue> getDictionary(DictionaryType type) {
    return dsl.select(DSL.field("code"), DSL.field("name"))
        .from(type.getTable())
        .fetchInto(DictionaryValue.class);
  }
}
