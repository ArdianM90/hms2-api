package com.hms.api.common.dictionary.service;

import com.hms.api.common.dictionary.dto.DictionaryValue;
import com.hms.api.common.dictionary.model.DictionaryType;
import java.util.List;

public interface DictionaryService {
  List<DictionaryValue> getDictionary(DictionaryType dictionaryType);
}
