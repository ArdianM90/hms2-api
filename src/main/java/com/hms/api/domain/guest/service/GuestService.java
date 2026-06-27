package com.hms.api.domain.guest.service;

import com.hms.api.common.dictionary.dto.DictionaryValue;
import com.hms.api.domain.guest.dto.CheckInRequest;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface GuestService {

  @Transactional
  void checkInGuests(CheckInRequest request);

  List<DictionaryValue> getDocumentTypes();

  List<DictionaryValue> getCitizenshipList();
}
