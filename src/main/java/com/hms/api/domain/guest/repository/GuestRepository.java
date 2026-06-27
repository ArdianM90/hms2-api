package com.hms.api.domain.guest.repository;

import com.hms.api.domain.guest.dto.ReservationRoomDto;
import java.util.List;

public interface GuestRepository {

  void checkInGuests(List<ReservationRoomDto> quests);
}
