package com.hms.api.common.jackson;

import com.hms.api.common.dictionary.model.DictionaryType;
import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DictionaryTypeConverter implements Converter<String, DictionaryType> {
  @Override
  public DictionaryType convert(@NonNull String source) {
    return DictionaryType.fromCode(source);
  }
}
