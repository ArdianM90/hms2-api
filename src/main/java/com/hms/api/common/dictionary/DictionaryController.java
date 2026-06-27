package com.hms.api.common.dictionary;

import com.hms.api.common.dictionary.dto.DictionaryValue;
import com.hms.api.common.dictionary.model.DictionaryType;
import com.hms.api.common.dictionary.service.DictionaryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dictionaries")
@RequiredArgsConstructor
public class DictionaryController {

  private final DictionaryService dictionaryService;

  @GetMapping("/{dictionaryType}")
  public ResponseEntity<List<DictionaryValue>> getDictionary(
      @PathVariable DictionaryType dictionaryType) {

    return ResponseEntity.ok(dictionaryService.getDictionary(dictionaryType));
  }
}
