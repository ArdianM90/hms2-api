package com.hms.api.domain.guest.service;

import com.hms.api.domain.guest.dto.CheckInRequest;
import org.springframework.transaction.annotation.Transactional;

public interface GuestService {

  @Transactional
  void checkInGuests(CheckInRequest request);
}
